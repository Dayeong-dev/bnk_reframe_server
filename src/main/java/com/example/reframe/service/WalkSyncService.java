package com.example.reframe.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.entity.ProductApplication;
import com.example.reframe.entity.deposit.DepositPaymentLog;
import com.example.reframe.repository.ProductApplicationRepository;
import com.example.reframe.repository.deposit.DepositPaymentLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalkSyncService {

    private static final BigDecimal MONTHLY_BONUS = new BigDecimal("0.83"); // 매월 우대 0.83%p

    private final ProductApplicationRepository appRepo;
    private final DepositPaymentLogRepository logRepo;

    /**
     * 버튼 클릭 시 오늘의 "누적 걸음수"만 받아서
     * - 이달(회차) 누적에 증분 반영
     * - 임계치(10만/5만) 달성 시 우대 0.83%p 확정
     * - preferential/effective 동시 갱신
     */
    @Transactional
    public Result sync(long appId, long stepsTodayTotal) {
        ProductApplication app = appRepo.findById(appId)
            .orElseThrow(() -> new IllegalArgumentException("application not found: " + appId));
        if (app.getStartAt() == null) throw new IllegalStateException("가입 시작일이 지정되지 않았습니다.");
        if (app.getTermMonthsAtEnroll() == null) throw new IllegalStateException("가입 개월이 지정되지 않았습니다.");

        // 타임존 일관화
        ZoneId KST = ZoneId.of("Asia/Seoul");
        LocalDateTime nowTs = LocalDateTime.now(KST);
        LocalDate today = nowTs.toLocalDate();

        // 회차 계산
        int round = resolveRound(app.getStartAt().toLocalDate(), today, app.getTermMonthsAtEnroll());

        // 회차 로그 잠금
        DepositPaymentLog log = logRepo.findByApplicationIdAndRoundForUpdate(appId, round)
            .orElseThrow(() -> new IllegalStateException("payment log not found: round=" + round));

        // 같은 '날짜'인지 비교 (시간 비교 X)
        LocalDate lastSyncDay = (log.getWalkLastSyncDate() != null) ? log.getWalkLastSyncDate().toLocalDate() : null;
        boolean sameDay = (lastSyncDay != null && lastSyncDay.equals(today));

        // 같은 날이면 캐시를 기준으로 증분, 하루가 바뀌었으면 0에서 시작
        long lastTodayCache = sameDay ? nvlLong(log.getWalkLastSyncSteps()) : 0L;
        long delta = Math.max(0, stepsTodayTotal - lastTodayCache);

        // 누적 반영 + 캐시 갱신
        log.setWalkStepsTotal(nvlLong(log.getWalkStepsTotal()) + delta);
        log.setWalkLastSyncSteps(stepsTodayTotal);
        log.setWalkLastSyncDate(nowTs);  // LocalDateTime로 저장 유지

        // 임계치(없으면 세팅)
        long threshold = nvlLong(log.getWalkThresholdSteps());
        if (threshold == 0) {
            LocalDate cycleEnd = cycleEndOf(app.getStartAt().toLocalDate(), round);
            boolean senior = isSeniorAt(app.getUser().getBirth(), cycleEnd);
            threshold = senior ? 50_000 : 100_000;
            log.setWalkThresholdSteps(threshold);
        }

        // 우대 확정
        if (!"Y".equals(log.getWalkConfirmedYn()) && nvlLong(log.getWalkStepsTotal()) >= threshold) {
            log.setWalkBonusApplied(MONTHLY_BONUS);
            log.setWalkConfirmedYn("Y");
            log.setWalkConfirmedAt(nowTs);
        }

        // 금리 계산
        BigDecimal base     = nvl(app.getBaseRateAtEnroll());
        BigDecimal minRate  = nvl(app.getProduct().getMinRate());
        BigDecimal maxRate  = nvl(app.getProduct().getMaxRate());
        BigDecimal sumBonus = nvl(logRepo.sumConfirmedBonus(appId));
        BigDecimal roomToMax = maxRate.subtract(base);
        if (roomToMax.signum() < 0) roomToMax = BigDecimal.ZERO;

        BigDecimal pref = sumBonus.min(roomToMax).setScale(2, RoundingMode.HALF_UP);
        BigDecimal eff  = base.add(pref).max(minRate).min(maxRate).setScale(2, RoundingMode.HALF_UP);

        app.setPreferentialRateAnnual(pref);
        app.setEffectiveRateAnnual(eff);

        // 응답: 오늘값은 방금 받은 누적값 그대로 반환
        return new Result(
            appId, round,
            nvlLong(log.getWalkStepsTotal()),
            threshold,
            "Y".equals(log.getWalkConfirmedYn()),
            monthYearOf(app.getStartAt().toLocalDate(), round),
            pref, eff,
            stepsTodayTotal,
            nowTs
        );
    }

    
    @Transactional(readOnly = true)
    public Result summary(long appId) {
        ProductApplication app = appRepo.findById(appId)
            .orElseThrow(() -> new IllegalArgumentException("application not found: " + appId));
        if (app.getStartAt() == null) throw new IllegalStateException("가입 시작일이 지정되지 않았습니다.");
        if (app.getTermMonthsAtEnroll() == null) throw new IllegalStateException("가입 개월이 지정되지 않았습니다.");

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        int round = resolveRound(app.getStartAt().toLocalDate(), today, app.getTermMonthsAtEnroll());

        // FOR UPDATE 없이 조회 전용
        DepositPaymentLog log = logRepo.findByApplicationIdAndRound(appId, round)
            .orElse(null);

        long steps = (log != null && log.getWalkStepsTotal() != null) ? log.getWalkStepsTotal() : 0L;

        long threshold = 0L;
        if (log != null && log.getWalkThresholdSteps() != null) {
            threshold = log.getWalkThresholdSteps();
        } else {
            // 회차 로그에 없으면 앱 스냅샷(가입 시 저장) 또는 기본값
            Long snap = app.getWalkThresholdSteps();
            threshold = (snap != null && snap > 0) ? snap : 100_000L;
        }

        boolean confirmed = (log != null && "Y".equals(log.getWalkConfirmedYn()));
        String yyyymm = monthYearOf(app.getStartAt().toLocalDate(), round);
        
        // 오늘 걸음(서버가 마지막으로 동기화한 오늘 누적값)
        long todayStepsLastSynced =
                (log != null && today.equals(log.getWalkLastSyncDate().toLocalDate()))
                    ? nvlLong(log.getWalkLastSyncSteps())
                    : 0L; // 다른 날이면 0으로 초기화

        // 금리: 확정 보너스 합만 기준으로 계산(쓰기 없음)
        BigDecimal base     = nvl(app.getBaseRateAtEnroll());
        BigDecimal minRate  = nvl(app.getProduct().getMinRate());
        BigDecimal maxRate  = nvl(app.getProduct().getMaxRate());
        BigDecimal sumBonus = nvl(logRepo.sumConfirmedBonus(appId));
        BigDecimal roomToMax = maxRate.subtract(base);
        if (roomToMax.signum() < 0) roomToMax = BigDecimal.ZERO;

        BigDecimal pref = sumBonus.min(roomToMax).setScale(2, RoundingMode.HALF_UP);
        BigDecimal eff  = base.add(pref).max(minRate).min(maxRate).setScale(2, RoundingMode.HALF_UP);

        return new Result(appId, round, steps, threshold, confirmed, yyyymm, pref, eff, todayStepsLastSynced, (log != null ? log.getWalkLastSyncDate() : null));
    }

    // ---------- Helpers ----------

	private static int resolveRound(LocalDate start, LocalDate today, int termMonths) {
	    if (today.isBefore(start)) {
	        throw new IllegalArgumentException("date before contract start");
	    }
	    int months = (int) ChronoUnit.MONTHS.between(start, today); // [start, start+1M, start+2M ...)
	    int round = months + 1; // 1..term
	    if (round < 1 || round > termMonths) {
	        throw new IllegalArgumentException("date out of contract period: round=" + round);
	    }
	    return round;
	}

	private static LocalDate cycleStartOf(LocalDate start, int round) {
	    return start.plusMonths(round - 1); // inclusive
	}

	private static LocalDate cycleEndOf(LocalDate start, int round) {
	    return start.plusMonths(round).minusDays(1); // inclusive (다음달 같은 날 '이전까지')
	}
	
	private static String monthYearOf(LocalDate start, int round) {
	    LocalDate cycleStart = cycleStartOf(start, round);
	    return String.format("%04d%02d", cycleStart.getYear(), cycleStart.getMonthValue()); // "yyyyMM"
	}

    private static boolean isSeniorAt(LocalDate birth, LocalDate onDate) {
    	if (birth == null) return false;
    	return Period.between(birth, onDate).getYears() >= 60;
    }

    private static long nvlLong(Long v) {
        return v == null ? 0L : v;
    }

    private static BigDecimal nvl(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }

    // --------- 서비스 결과 DTO (컨트롤러 응답으로 변환) ---------
    public record Result(
            Long appId,
            Integer round,
            Long stepsThisMonth,
            Long threshold,
            Boolean confirmedThisMonth,
            String yyyymm,
            BigDecimal preferentialRate,
            BigDecimal effectiveRate, 
            Long todayStepsLastSynced,
            LocalDateTime lastSyncDate	// (선택) 마지막 동기화 일자
    ) {}
}