// com.example.reframe.dto.report.AdminReport
package com.example.reframe.dto.report;

import java.time.YearMonth;
import java.util.*;

public class AdminReport {
  /** 리포트 대상 연-월 (예: 2025-05) */
  public YearMonth ym;

  // ===== PDF 요약 =====
  /** 신규 가입자 수 (해당 월) */
  public long newJoiners;
  /** 증감 문구 (예: "(전월 대비 +12%)") */
  public String momChangeText;
  /** 총 가입자 수 (월말 기준 누적) */
  public long totalJoiners;

  /** 연령대/성별 Top3 (예: "30대 여성") — 현재 스키마 제약 시 빈 리스트 */
  public List<String> top3AgeGender = new ArrayList<>();

  /** 전체 남녀 비율 텍스트 (예: "4:6") — 현재 스키마 제약 시 기본값 */
  public String genderRatioText;

  /** 가입 Top3 상품 (해당 월 기준) */
  public static class TopItem {
    public String name;
    public int count;
  }
  /** (월간) 가입 Top3 */
  public List<TopItem> top3Joined = new ArrayList<>();
  /** (월간) 조회수 Top3 — 로그 기반 월간 집계 */
  public List<TopItem> top3Viewed = new ArrayList<>();

  /** 평균 별점 (해당 월) */
  public double avgRating;
  /** 긍정/부정 비율 텍스트 (예: "7:3") — 해당 월 */
  public String sentimentRatioText;

  // ===== 엑셀 표 =====
  /** 가입자 현황 (연령대 x 성별) — 화면처럼 “10대/20대/…/전체” */
  public static class AgeGenderRow {
    public String ageBand;
    public Integer male;
    public Integer female;
    public Integer total;
    /** 최근월 값 등 필요 시 사용 */
    public Integer recentMonth;
  }
  public List<AgeGenderRow> subscriberTable = new ArrayList<>();

  // ===== 전체 순위표 (엑셀) =====
  /** (월간) 가입 건수 전체 순위 */
  public List<TopItem> joinedRanking = new ArrayList<>();
  /** (월간) 조회수 전체 순위 — 반드시 로그 기반 집계 사용 */
  public List<TopItem> viewedRanking = new ArrayList<>();

  // ===== 최근 3개월 별점 분포 =====
  /** 월별(키: "1월"/"2월" 등) × 별점(1~5) → 건수 */
  public Map<String, Map<Integer, Integer>> ratingsByMonth = new LinkedHashMap<>();

  // ===== 긍/부정 비율 (수치) =====
  public int positivePercent;
  public int negativePercent;
}
