package com.example.reframe.service;

import com.example.reframe.dto.report.AdminReport;
import com.example.reframe.dto.report.AdminReport.TopItem;
import com.example.reframe.repository.*;
import com.example.reframe.repository.auth.UserRepository;

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

  public ReportService(DepositProductRepository productRepo,
                       ProductReviewRepository reviewRepo,
                       UserRepository userRepo) {
    this.productRepo = productRepo;
    this.reviewRepo = reviewRepo;
    this.userRepo = userRepo;
  }

  public AdminReport buildMonthly(YearMonth ym){
    // Date 범위 (java.util.Date 로 변환)
    LocalDateTime fromLdt = ym.atDay(1).atStartOfDay();
    LocalDateTime toLdt   = ym.atEndOfMonth().atTime(23,59,59);
    Date from = Date.from(fromLdt.atZone(ZoneId.systemDefault()).toInstant());
    Date to   = Date.from(toLdt.atZone(ZoneId.systemDefault()).toInstant());

    AdminReport r = new AdminReport();
    r.ym = ym;

    // ===== 1) 신규/총 가입자 수 (현재 스키마로는 불가) =====
    r.newJoiners = 0;                         // TODO: ProductApplication 생기면 교체
    r.momChangeText = "(전월 대비 0%)";        // TODO
    r.totalJoiners = 0;                       // TODO

    // ===== 2) 연령/성별 Top3 & 성비 (현재 스키마로는 '가입자' 기준 불가) =====
    // 대안 없음 → 빈 값/기본값 처리
    r.top3AgeGender = List.of();              // TODO: 가입 로그 생기면 집계
    r.genderRatioText = "0:0";                // TODO

    // ===== 3) 가입 Top3 (임시: 리뷰 수 상위 3개 상품) =====
    r.top3Joined = toTopItemsByReviewCount(reviewRepo.topProductsByReviewIn(from, to), 3);

    // ===== 4) 조회 Top3 (누적 viewCount 상위) =====
    r.top3Viewed = toTopItemsByViewCount(productRepo.topViewedAll(), 3);

    // ===== 5) 평균 별점 (그 달) =====
    r.avgRating = Optional.ofNullable(reviewRepo.avgRatingIn(from, to)).orElse(0.0);

    // ===== 6) 최근 3개월 별점 분포 =====
    r.ratingsByMonth = new LinkedHashMap<>();
    for(int i=2; i>=0; i--){
      YearMonth ym2 = ym.minusMonths(i);
      LocalDateTime fL = ym2.atDay(1).atStartOfDay();
      LocalDateTime tL = ym2.atEndOfMonth().atTime(23,59,59);
      Date f = Date.from(fL.atZone(ZoneId.systemDefault()).toInstant());
      Date t = Date.from(tL.atZone(ZoneId.systemDefault()).toInstant());

      String title = ym2.getMonthValue() + "월";
      Map<Integer,Integer> dist = toRatingDist(reviewRepo.ratingDistIn(f,t));
      r.ratingsByMonth.put(title, dist);
    }

    // ===== 7) 긍/부정 비율 (negative boolean) =====
    var sentiRows = reviewRepo.sentimentCountsIn(from, to);
    int pos = 0, neg = 0;
    if (sentiRows != null){
      for (Object[] a : sentiRows){
        Boolean negative = (Boolean)a[0];
        Long cnt = (Long)a[1];
        if (negative != null && negative) neg += cnt.intValue();
        else pos += cnt.intValue();
      }
    }
    int tot = Math.max(1, pos+neg);
    r.positivePercent = Math.round(pos * 100f / tot);
    r.negativePercent = 100 - r.positivePercent;
    r.sentimentRatioText = ratioText(r.positivePercent, r.negativePercent);

    // ===== 8) 엑셀 1번 표(연령대/성별) — 현재 스키마로 정확 집계 불가 → 빈/요약행만 =====
    r.subscriberTable = List.of(); // TODO

    // ===== 9) 엑셀 2번 표 (Top5) =====
    r.top5Joined = toTopItemsByReviewCount(reviewRepo.topProductsByReviewIn(from, to), 5);
    r.top5Viewed = toTopItemsByViewCount(productRepo.topViewedAll(), 5);

    return r;
  }

  // ---- helpers ----
  private List<TopItem> toTopItemsByReviewCount(List<Object[]> rows, int limit){
	    if (rows == null) return List.of();
	    return rows.stream().limit(limit).map(a -> {
	      Long pid = ((Number) a[0]).longValue();          // ✅ 안전 캐스팅
	      long cnt = ((Number) a[1]).longValue();          // ✅ 안전 캐스팅
	      String name = Optional.ofNullable(productRepo.findNameById(pid)).orElse("상품("+pid+")");
	      TopItem t = new TopItem(); t.name = name; t.count = (int)cnt; return t;
	    }).collect(Collectors.toList());
	  }

	  private List<TopItem> toTopItemsByViewCount(List<Object[]> rows, int limit){
	    if (rows == null) return List.of();
	    return rows.stream().limit(limit).map(a -> {
	      Long pid = ((Number) a[0]).longValue();          // ✅ 안전 캐스팅
	      long cnt = ((Number) a[1]).longValue();          // ✅ coalesce 결과가 Integer/BigDecimal 여도 OK
	      String name = Optional.ofNullable(productRepo.findNameById(pid)).orElse("상품("+pid+")");
	      TopItem t = new TopItem(); t.name = name; t.count = (int)cnt; return t;
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
    return (posPct/g)+":"+(negPct/g);
  }
  private int gcd(int a,int b){ return b==0? a: gcd(b, a%b); }
}

