// com.example.reframe.export.ExcelReportWriter
package com.example.reframe.export;

import com.example.reframe.dto.report.AdminReport;
import com.example.reframe.dto.report.AdminReport.AgeGenderRow;
import com.example.reframe.dto.report.AdminReport.TopItem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class ExcelReportWriter {

  public void write(AdminReport r, OutputStream os) throws Exception {
    try (Workbook wb = new XSSFWorkbook()) { // ✅ XSSF 사용 (autoSizeColumn 안정)
      // 공통 스타일
      CellStyle head = wb.createCellStyle();
      Font bold = wb.createFont(); bold.setBold(true);
      head.setFont(bold);
      head.setAlignment(HorizontalAlignment.CENTER);
      head.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
      head.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      head.setBorderBottom(BorderStyle.THIN); head.setBorderTop(BorderStyle.THIN);
      head.setBorderLeft(BorderStyle.THIN);   head.setBorderRight(BorderStyle.THIN);

      CellStyle cell = wb.createCellStyle();
      cell.setBorderBottom(BorderStyle.THIN); cell.setBorderTop(BorderStyle.THIN);
      cell.setBorderLeft(BorderStyle.THIN);   cell.setBorderRight(BorderStyle.THIN);

      /* ===== 1) 가입자 현황 ===== */
      String title1 = "최근 한달 가입자 수 / 전체 가입자 수 / 연령대/성별"; // 내부 표시용(슬래시 허용)
      Sheet s1 = wb.createSheet(safeSheetName("1. 가입자 현황"));            // 시트명은 금지문자 치환
      int rIdx = 0;
      addTitleRow(s1, rIdx++, title1, wb); // 내부 첫 행에 원래 제목 그대로 기록
      rIdx++; // 빈 줄

      Row h = row(s1, rIdx++);
      set(h,0,"연령대",head); set(h,1,"성별",head); set(h,2,"총 가입자 수",head); set(h,3,"최근 1개월 가입자 수",head);

      for (AgeGenderRow ag : safe(r.subscriberTable)) {
        Row rr = row(s1, rIdx++);
        set(rr,0,ag.ageBand,cell);
        set(rr,1,"-", cell);
        set(rr,2,num(ag.total),cell);
        set(rr,3,num(ag.recentMonth),cell);
      }
      autosizeSafe(s1, 4);

      /* ===== 2) 판매/조회 현황 ===== */
      String title2 = "최근 한 달 가입 건수 전체 순위 / 누적 조회수 전체 순위";
      Sheet s2 = wb.createSheet(safeSheetName("2. 상품 판매 현황"));
      rIdx = 0;
      addTitleRow(s2, rIdx++, title2, wb);
      rIdx++;

      // 왼쪽: 가입 "전체" 순위
      int startL = rIdx;
      Row h1 = row(s2, rIdx++);
      set(h1,0,"순위",head); set(h1,1,"상품명",head); set(h1,2,"가입건수",head);

      int rank = 1;
      for (TopItem t : (r.joinedRanking == null ? List.<TopItem>of() : r.joinedRanking)) {
        Row rr = row(s2, rIdx++);
        set(rr,0,rank++,cell);
        set(rr,1,str(t.name),cell);
        set(rr,2,num(t.count),cell);
      }

      // 오른쪽: 조회 "전체" 순위 (같은 행에 4열부터)
      int rIdxRight = startL;
      Row h2 = row(s2, rIdxRight++);
      set(h2,4,"순위",head); set(h2,5,"상품명",head); set(h2,6,"조회수",head);

      rank = 1;
      if (r.viewedRanking != null) {
        for (TopItem t : r.viewedRanking) {
          Row rr = row(s2, rIdxRight++);
          set(rr,4,rank++,cell);
          set(rr,5,str(t.name),cell);
          set(rr,6,num(t.count),cell);
        }
      }

      // 열 폭 자동 조정
      autosizeSafe(s2, 7);

      /* ===== 3) 리뷰 ===== */
      String title3 = "평균 평점 (최근 3개월, 1~5점 분포)";
      Sheet s3 = wb.createSheet(safeSheetName("3. 리뷰"));
      rIdx = 0;
      addTitleRow(s3, rIdx++, title3, wb);
      rIdx++;

      // 헤더: 등급/월
      Row h3 = row(s3, rIdx++);
      set(h3,0,"등급",head);
      int c=1; for (String m : r.ratingsByMonth.keySet()) set(h3,c++, m, head);

      for (int star=1; star<=5; star++){
        Row rr = row(s3, rIdx++);
        set(rr,0, star, cell);
        c=1;
        for (Map<Integer,Integer> dist : r.ratingsByMonth.values()) {
          set(rr,c++, num(dist.getOrDefault(star,0)), cell);
        }
      }
      rIdx += 1;
      Row sentiTitle = row(s3, rIdx++); set(sentiTitle,0, "긍정/부정 비율", null);
      Row sentiHead  = row(s3, rIdx++); set(sentiHead,0,"긍정",head); set(sentiHead,1,"부정",head);
      Row senti      = row(s3, rIdx++); set(senti,0, r.positivePercent+"%", cell); set(senti,1, r.negativePercent+"%", cell);
      autosizeSafe(s3, Math.max(2 + r.ratingsByMonth.size(), 4));

      wb.write(os);
    }
  }

  /* ================= helpers ================= */

  private static Row row(Sheet s, int idx){
    return s.getRow(idx)==null? s.createRow(idx): s.getRow(idx);
  }

  private static void set(Row r, int c, Object v, CellStyle st){
    Cell cell = r.createCell(c);
    if (v instanceof Number n) cell.setCellValue(n.doubleValue());
    else cell.setCellValue(v==null? "" : String.valueOf(v));
    if (st != null) cell.setCellStyle(st);
  }

  private static Integer num(Integer v){ return v==null? 0: v; }
  private static String  str(String v){ return v==null? "": v; }

  private static List<AdminReport.TopItem> padTo5(List<AdminReport.TopItem> src){
    if (src == null) return List.of();
    if (src.size() >= 5) return src;
    var copy = new java.util.ArrayList<AdminReport.TopItem>(src);
    for (int i=src.size(); i<5; i++){
      var t = new AdminReport.TopItem(); t.name=""; t.count=0; copy.add(t);
    }
    return copy;
  }

  private static List<AgeGenderRow> safe(List<AgeGenderRow> v){
    return v==null? List.of(): v;
  }

  // 시트 이름을 엑셀 제약에 맞게 정리 (금지문자 치환 + 길이 제한 + 트림)
  private static String safeSheetName(String name){
    if (name == null) return "Sheet";
    String s = name
      .replaceAll("[\\\\/?*\\[\\]:]", "-") // 금지문자 → "-"
      .replaceAll("\\p{Cntrl}", "")        // 제어문자 제거
      .trim();
    if (s.isEmpty()) s = "Sheet";
    if (s.length() > 31) s = s.substring(0, 31); // 엑셀 시트명 최대 31자
    return s;
  }

  // 제목행(원래 텍스트)을 시트 내부에 기록 (슬래시 그대로 표시)
  private static void addTitleRow(Sheet s, int rowIdx, String title, Workbook wb){
    Row r = row(s, rowIdx);
    Cell c = r.createCell(0);
    c.setCellValue(title == null ? "" : title);
    // 굵게 스타일(선택)
    CellStyle st = wb.createCellStyle();
    Font ft = wb.createFont(); ft.setBold(true); st.setFont(ft);
    r.getCell(0).setCellStyle(st);
  }

  private static void autosizeSafe(Sheet s, int cols){
    for(int i=0;i<cols;i++){
      try { s.autoSizeColumn(i); }
      catch (Exception e) { s.setColumnWidth(i, 5000); } // 실패 시 기본폭
    }
  }
}
