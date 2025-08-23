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
    try (Workbook wb = new XSSFWorkbook()) {
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

      /* === 1) 가입자 현황 시트 === */
      Sheet s1 = wb.createSheet(safeSheetName("1. 가입자 현황"));
      int rIdx = 0;
      addTitleRow(s1, rIdx++, "최근 한달 가입자 수 / 전체 가입자 수 / 연령대/성별", wb);
      rIdx++;

      Row h = row(s1, rIdx++);
      set(h,0,"연령대",head);
      set(h,1,"성별",head);
      set(h,2,"총 가입자 수",head);
      set(h,3,"최근 1개월 가입자 수",head);

      for (AgeGenderRow ag : safe(r.subscriberTable)) {
        boolean hasGenderSplit = (ag.male != null || ag.female != null);

        if (hasGenderSplit) {
          // (A) 남/여 두 줄
          Row m = row(s1, rIdx++);
          set(m,0,ag.ageBand,cell); set(m,1,"남",cell);
          set(m,2,num(ag.male),cell); set(m,3,"",cell); // 최근 1개월 성별 분해가 없으면 공란

          Row f = row(s1, rIdx++);
          set(f,0,ag.ageBand,cell); set(f,1,"여",cell);
          set(f,2,num(ag.female),cell); set(f,3,"",cell);

          // 합계 라인
          Row sum = row(s1, rIdx++);
          set(sum,0,ag.ageBand,cell); set(sum,1,"-",cell);
          set(sum,2,num(ag.total),cell); set(sum,3,num(ag.recentMonth),cell);
        } else {
          // (B) 성별 집계가 없으면 한 줄
          Row rr = row(s1, rIdx++);
          set(rr,0,ag.ageBand,cell); set(rr,1,"-",cell);
          set(rr,2,num(ag.total),cell); set(rr,3,num(ag.recentMonth),cell);
        }
      }
      // ✅ 헤더가 긴 시트1은 최소 폭 보장 + 정확 자동조정
      autosizeWithMin(s1,
          new int[]{0,1,2,3},                // 컬럼 인덱스
          new int[]{4000,3500,5000,8000});   // 최소 폭(단위: 1/256 char) ≈ 15~31자 느낌

      /* ===== 2) 판매/조회 현황 ===== */
      String title2 = "최근 한 달 가입 건수 전체 순위 / 최근 한 달 조회수 전체 순위";
      Sheet s2 = wb.createSheet(safeSheetName("2. 상품 현황"));
      rIdx = 0;
      addTitleRow(s2, rIdx++, title2, wb);
      rIdx++;

      // 왼쪽: 가입 "전체" 순위 (월간)
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

      // 오른쪽: 조회 "전체" 순위 (월간)
      int rIdxRight = startL;
      Row h2 = row(s2, rIdxRight++);
      set(h2,4,"순위",head); set(h2,5,"상품명",head);
      set(h2,6,"월간 조회수",head);

      rank = 1;
      if (r.viewedRanking != null) {
        for (TopItem t : r.viewedRanking) {
          Row rr = row(s2, rIdxRight++);
          set(rr,4,rank++,cell);
          set(rr,5,str(t.name),cell);
          set(rr,6,num(t.count),cell);
        }
      }
      autosizeSafe(s2, 7); // 정확 자동조정

      /* === 3) 상품 가입 리뷰 시트 === */
      Sheet s3 = wb.createSheet(safeSheetName("3. 상품가입 리뷰"));
      rIdx = 0;
      addTitleRow(s3, rIdx++, "평균 평점 / 최근 3개월 별점 분포 / 긍정·부정 비율", wb);
      rIdx++;

      // 월별 별점 분포 표
      Row h3 = row(s3, rIdx++);
      set(h3,0,"등급",head);
      int c=1; for (String mKey : r.ratingsByMonth.keySet()) set(h3,c++, mKey, head);

      for (int star=1; star<=5; star++){
        Row rr = row(s3, rIdx++);
        set(rr,0, star+"점", cell);
        c=1;
        for (Map<Integer,Integer> dist : r.ratingsByMonth.values()) {
          set(rr,c++, num(dist.getOrDefault(star,0)), cell);
        }
      }

      // 긍/부정 비율
      rIdx += 1;
      Row sentiTitle = row(s3, rIdx++); set(sentiTitle,0, "긍정/부정 비율", null);
      Row sentiHead  = row(s3, rIdx++); set(sentiHead,0,"긍정",head); set(sentiHead,1,"부정",head);
      Row senti      = row(s3, rIdx++); set(senti,0, r.positivePercent+"%", cell); set(senti,1, r.negativePercent+"%", cell);
      autosizeSafe(s3, Math.max(2 + r.ratingsByMonth.size(), 4));

      wb.write(os);
    }
  }

  /* ===== helpers ===== */
  private static Row row(Sheet s, int idx){ return s.getRow(idx)==null? s.createRow(idx): s.getRow(idx); }
  private static void set(Row r, int c, Object v, CellStyle st){
    Cell cell = r.createCell(c);
    if (v instanceof Number n) cell.setCellValue(n.doubleValue());
    else cell.setCellValue(v==null? "" : String.valueOf(v));
    if (st != null) cell.setCellStyle(st);
  }
  private static Integer num(Integer v){ return v==null? 0: v; }
  private static Integer num(int v){ return v; }
  private static String  str(String v){ return v==null? "": v; }
  private static List<AgeGenderRow> safe(List<AgeGenderRow> v){ return v==null? List.of(): v; }

  // 시트명 금지문자 치환 + 길이 제한
  private static String safeSheetName(String name){
    if (name == null) return "Sheet";
    String s = name.replaceAll("[\\\\/?*\\[\\]:]", "-").replaceAll("\\p{Cntrl}", "").trim();
    if (s.isEmpty()) s = "Sheet";
    if (s.length() > 31) s = s.substring(0, 31);
    return s;
  }
  private static void addTitleRow(Sheet s, int rowIdx, String title, Workbook wb){
    Row r = row(s, rowIdx); Cell c = r.createCell(0); c.setCellValue(title == null ? "" : title);
    CellStyle st = wb.createCellStyle(); Font ft = wb.createFont(); ft.setBold(true); st.setFont(ft);
    c.setCellStyle(st);
  }

  /** 모든 열: rich text/merge 고려한 정확 자동조정 */
  private static void autosizeSafe(Sheet s, int cols){
    for(int i=0;i<cols;i++){
      try { s.autoSizeColumn(i, true); } // ✅ 핵심: true
      catch (Exception e) { s.setColumnWidth(i, 5000); }
    }
  }

  /** 특정 열들: autoSize 후 최소 폭 보장 (헤더가 긴 시트1 대응) */
  private static void autosizeWithMin(Sheet s, int[] columns, int[] minWidths){
    for (int i = 0; i < columns.length; i++){
      int col = columns[i];
      int min = (i < minWidths.length ? minWidths[i] : 5000);
      try {
        s.autoSizeColumn(col, true);                  // 우선 정확 자동조정
        int w = s.getColumnWidth(col);
        if (w < min) s.setColumnWidth(col, min);      // 최소폭 보정
      } catch (Exception e){
        s.setColumnWidth(col, min);
      }
    }
  }
}
