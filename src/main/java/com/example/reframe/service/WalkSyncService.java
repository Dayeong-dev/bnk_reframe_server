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
    	
        // 1) 가입건 조회
        ProductApplication app = appRepo.findById(appId)
                .orElseThrow(() -> new IllegalArgumentException("application not found: " + appId));

        if (app.getStartAt() == null) throw new IllegalStateException("가입 시작일이 지정되지 않았습니다.");
    	if (app.getTermMonthsAtEnroll() == null) throw new IllegalStateException("가입 개월이 지정되지 않았습니다.");
    	
        
        // 2) 오늘 날짜가 속한 회차(1..term) 계산 (가입일 기준, 다음달 같은 날 이전까지)
        LocalDate today = LocalDate.now();
        int round = resolveRound(app.getStartAt().toLocalDate(), today, app.getTermMonthsAtEnroll());

        // 3) 회차 로그 락 획득
        DepositPaymentLog log = logRepo.findByApplicationIdAndRoundForUpdate(appId, round)
                .orElseThrow(() -> new IllegalStateException("payment log not found: round=" + round));

        // 4) 증분 반영 (같은 날 반복 클릭은 diff만)
        long delta = today.equals(log.getWalkLastSyncDate())
                ? Math.max(0, stepsTodayTotal - nvlLong(log.getWalkLastSyncSteps()))
                : Math.max(0, stepsTodayTotal);

        log.setWalkStepsTotal(nvlLong(log.getWalkStepsTotal()) + delta);
        log.setWalkLastSyncDate(today);
        log.setWalkLastSyncSteps(stepsTodayTotal);

        // 5) 임계치 산정(사이클 종료일 기준 나이)
        long threshold = nvlLong(log.getWalkThresholdSteps());
        if (threshold == 0) {
            LocalDate cycleEnd = cycleEndOf(app.getStartAt().toLocalDate(), round);
            boolean senior = isSeniorAt(app.getUser().getBirth(), cycleEnd);
            threshold = senior ? 50_000 : 100_000;
            log.setWalkThresholdSteps(threshold);
        }

        // 6) 달성 시 우대 확정(1회만)
        if (!"Y".equals(log.getWalkConfirmedYn()) && nvlLong(log.getWalkStepsTotal()) >= threshold) {
            log.setWalkBonusApplied(MONTHLY_BONUS);
            log.setWalkConfirmedYn("Y");
            log.setWalkConfirmedAt(LocalDateTime.now());
        }

        // 7) 금리 갱신 (둘 다!)
        BigDecimal base     = nvl(app.getBaseRateAtEnroll());
        BigDecimal minRate  = nvl(app.getProduct().getMinRate());
        BigDecimal maxRate  = nvl(app.getProduct().getMaxRate());
        BigDecimal sumBonus = nvl(logRepo.sumConfirmedBonus(appId)); // 확정된 월들의 보너스 합(0.83씩)

        // 우대금리는 '최대금리 - 기준금리'를 초과할 수 없음
        BigDecimal roomToMax = maxRate.subtract(base);
        if (roomToMax.signum() < 0) roomToMax = BigDecimal.ZERO;

        BigDecimal pref = sumBonus.min(roomToMax).setScale(2, RoundingMode.HALF_UP);
        BigDecimal eff  = base.add(pref)
                              .max(minRate)
                              .min(maxRate)
                              .setScale(2, RoundingMode.HALF_UP);

        app.setPreferentialRateAnnual(pref);
        app.setEffectiveRateAnnual(eff);
        // @Transactional 이므로 flush 시 자동 반영

        return new Result(
                appId,
                round,
                nvlLong(log.getWalkStepsTotal()),
                threshold,
                "Y".equals(log.getWalkConfirmedYn()),
                monthYearOf(app.getStartAt().toLocalDate(), round),
                pref,
                eff
        );
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
            BigDecimal effectiveRate
    ) {}
}