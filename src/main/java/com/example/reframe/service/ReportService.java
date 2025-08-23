package com.example.reframe.service;

import com.example.reframe.dto.report.AdminReport;
import com.example.reframe.dto.report.AdminReport.TopItem;
import com.example.reframe.repository.DepositProductRepository;
import com.example.reframe.repository.ProductReviewRepository;
import com.example.reframe.repository.auth.UserRepository;
import com.example.reframe.repository.deposit.ProductViewLogRepository;   // ✅ 추가

import org.springframework.data.domain.PageRequest;  // ✅ 추가
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReportService {

  private final DepositProductRepository productRepo;
  private final ProductReviewRepository reviewRepo;
  private final UserRepository userRepo;
  private final ProductViewLogRepository viewLogRepo;   // ✅ 추가

  public ReportService(DepositProductRepository productRepo,
                       ProductReviewRepository reviewRepo,
                       UserRepository userRepo,
                       ProductViewLogRepository viewLogRepo) { // ✅ 추가
    this.productRepo = productRepo;
    this.reviewRepo = reviewRepo;
    this.userRepo = userRepo;
    this.viewLogRepo = viewLogRepo; // ✅ 추가
  }

  public AdminReport buildMonthly(YearMonth ym){
    // ✅ 기간 경계: [start, endExcl)
    LocalDateTime start   = ym.atDay(1).atStartOfDay();
    LocalDateTime endExcl = ym.plusMonths(1).atDay(1).atStartOfDay();

    // (리뷰 리포지토리는 java.util.Date를 받으므로 변환)
    Date from = toDate(start);
    Date to   = toDate(endExcl.minusNanos(1)); // 포함 배타 경계 보정

    AdminReport r = new AdminReport();
    r.ym = ym;

    // ===== 1) 신규/총 가입자 수 (현재 스키마로는 불가) =====
    r.newJoiners = 0;                  // TODO: 가입 소스 생기면 교체
    r.momChangeText = "(전월 대비 0%)"; // TODO
    r.totalJoiners = 0;                // TODO

    // ===== 2) 연령/성별 Top3 & 성비 (현재 스키마로 불가) =====
    r.top3AgeGender = List.of();       // TODO
    r.genderRatioText = "0:0";         // TODO

    // ===== 3) 가입 Top3 (임시: 리뷰 수 상위 3개 상품, 월간) =====
    r.top3Joined = toTopItemsByReviewCount(reviewRepo.topProductsByReviewIn(from, to), 3);

    // ===== 4) 월간 조회수 TOP3 (로그 기반) =====
    var rowsTopMonthly = viewLogRepo.topViewedBetween(start, endExcl, PageRequest.of(0, 3));
    r.top3Viewed = toTopItemsByViewCount(rowsTopMonthly, 3);

    // ===== 5) 평균 별점 (그 달) =====
    r.avgRating = Optional.ofNullable(reviewRepo.avgRatingIn(from, to)).orElse(0.0);

    // ===== 6) 최근 3개월 별점 분포 =====
    r.ratingsByMonth = new LinkedHashMap<>();
    for(int i=2; i>=0; i--){
      YearMonth ym2 = ym.minusMonths(i);
      LocalDateTime fL = ym2.atDay(1).atStartOfDay();
      LocalDateTime tL = ym2.plusMonths(1).atDay(1).atStartOfDay();
      Date f = toDate(fL);
      Date t = toDate(tL.minusNanos(1));

      String title = ym2.getMonthValue() + "월";
      Map<Integer,Integer> dist = toRatingDist(reviewRepo.ratingDistIn(f, t));
      r.ratingsByMonth.put(title, dist);
    }

    // ===== 7) 긍/부정 비율 (negative boolean, 월간) =====
    var sentiRows = reviewRepo.sentimentCountsIn(from, to);
    int pos = 0, neg = 0;
    if (sentiRows != null){
      for (Object[] a : sentiRows){
        Boolean negative = (Boolean)a[0];
        Long cnt = (Long)a[1];
        if (Boolean.TRUE.equals(negative)) neg += cnt.intValue();
        else pos += cnt.intValue();
      }
    }
    int tot = Math.max(1, pos+neg);
    r.positivePercent = Math.round(pos * 100f / tot);
    r.negativePercent = 100 - r.positivePercent;
    r.sentimentRatioText = ratioText(r.positivePercent, r.negativePercent);

    // ===== 8) 엑셀 1번 표(연령대/성별) — 현재 스키마 제약으로 빈값 =====
    r.subscriberTable = List.of();

    // ===== 9) 엑셀 2번 표 (월간 전체 랭킹, 로그 기반) =====
    var rowsAllMonthly = viewLogRepo.topViewedBetween(start, endExcl, PageRequest.of(0, 1000));
    r.viewedRanking = toTopItemsByViewCount(rowsAllMonthly, Integer.MAX_VALUE);

    // (참고) r.joinedRanking은 ‘월간 리뷰 수 기준’으로 유지
    r.joinedRanking = toTopItemsByReviewCount(reviewRepo.topProductsByReviewIn(from, to), Integer.MAX_VALUE);

    return r;
  }

  /* ===================== helpers ===================== */

  private static Date toDate(LocalDateTime ldt) {
    return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
  }

  private List<TopItem> toTopItemsByReviewCount(List<Object[]> rows, int limit){
    if (rows == null) return List.of();
    return rows.stream().limit(limit).map(a -> {
      Long pid = ((Number) a[0]).longValue();
      long cnt = ((Number) a[1]).longValue();
      String name = Optional.ofNullable(productRepo.findNameById(pid)).orElse("상품(" + pid + ")");
      TopItem t = new TopItem(); t.name = name; t.count = (int) cnt; return t;
    }).collect(Collectors.toList());
  }

  private List<TopItem> toTopItemsByViewCount(List<Object[]> rows, int limit){
    if (rows == null) return List.of();
    return rows.stream().limit(limit).map(a -> {
      Long pid = ((Number) a[0]).longValue();
      long cnt = ((Number) a[1]).longValue();
      String name = Optional.ofNullable(productRepo.findNameById(pid)).orElse("상품(" + pid + ")");
      TopItem t = new TopItem(); t.name = name; t.count = (int) cnt; return t;
    }).collect(Collectors.toList());
  }

  private Map<Integer,Integer> toRatingDist(List<Object[]> rows){
    Map<Integer,Integer> m = new LinkedHashMap<>();
    for (int i=1;i<=5;i++) m.put(i,0);
    if (rows != null){
      for (Object[] a : rows){
        Integer star = (Integer)a[0];
        Long cnt = (Long)a[1];
        if (star != null) m.put(star, cnt.intValue());
      }
    }
    return m;
  }

  private String ratioText(int posPct, int negPct){
    int g = gcd(posPct, negPct);
    return (posPct/g) + ":" + (negPct/g);
  }
  private int gcd(int a,int b){ return b==0? a: gcd(b, a%b); }
}
