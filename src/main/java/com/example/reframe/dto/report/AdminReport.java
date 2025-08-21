// com.example.reframe.dto.report.AdminReport
package com.example.reframe.dto.report;

import java.time.YearMonth;
import java.util.*;

public class AdminReport {
  public YearMonth ym;

  // ===== PDF 요약 =====
  public long newJoiners;                 // 신규 가입자 수
  public String momChangeText;            // "(전월 대비 +12%)" 형식의 문구
  public long totalJoiners;               // 총 가입자 수

  // 연령대/성별 Top3 (예: "30대 여성")
  public List<String> top3AgeGender = new ArrayList<>();

  // 전체 남녀 비율 (예: "4:6")
  public String genderRatioText;

  // 가입 Top3 상품
  public static class TopItem { public String name; public int count; }
  public List<TopItem> top3Joined = new ArrayList<>();
  public List<TopItem> top3Viewed = new ArrayList<>();

  public double avgRating;                // 평균 별점 (예: 4.3)
  public String sentimentRatioText;       // "긍정 부정 비율 7:3"

  // ===== 엑셀 표 =====
  // 가입자 현황 (연령대 x 성별)
  public static class AgeGenderRow {
    public String ageBand; public Integer male; public Integer female; public Integer total; public Integer recentMonth;
  }
  public List<AgeGenderRow> subscriberTable = new ArrayList<>(); // 화면처럼 “10대/20대/…/전체”

  // 판매/조회 Top5
  public List<TopItem> top5Joined = new ArrayList<>();
  public List<TopItem> top5Viewed = new ArrayList<>();

  // 월별 별점 분포 (최근 3개월 × 1~5점)
  // 예: ratingsByMonth.get("1월").get(5) = 45
  public Map<String, Map<Integer, Integer>> ratingsByMonth = new LinkedHashMap<>();

  // 긍/부정 비율
  public int positivePercent; public int negativePercent;
}
