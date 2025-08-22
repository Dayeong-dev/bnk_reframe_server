// com.example.reframe.export.PdfReportWriter
package com.example.reframe.export;

import com.example.reframe.dto.report.AdminReport;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.OutputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PdfReportWriter {
  private volatile BaseFont baseFont;

  /* ========= 폰트 ========= */
  private synchronized void ensureFont() {
    if (baseFont != null) return;
    try {
      URL url = getClass().getResource("/fonts/NanumGothic-Regular.ttf");
      if (url == null) throw new IllegalStateException("폰트 파일 없음: /fonts/NanumGothic-Regular.ttf");
      baseFont = BaseFont.createFont(url.toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    } catch (Exception e) { throw new IllegalStateException("한글 폰트 등록 실패", e); }
  }
  private Font font(float size, int style) { ensureFont(); return new Font(baseFont, size, style); }
  private Font font(float size, int style, java.awt.Color color) { ensureFont(); return new Font(baseFont, size, style, color); }

  /* ========= 색상/유틸 ========= */
  private static final java.awt.Color BLUE = new java.awt.Color(0x40, 0x70, 0xFD); // #4070FD
  private static String nf(long v){ return NumberFormat.getNumberInstance(Locale.KOREA).format(v); }
  private static String nfd(double v){ return NumberFormat.getNumberInstance(Locale.KOREA).format(v); }
  private static String nz(String s){ return (s==null || s.isBlank()) ? "" : s; }

  /** "라벨: 값(파란색)" — 모두 일반체, 값만 파란색 */
  private Paragraph kvLine(String label, String value) {
    Phrase ph = new Phrase();
    ph.add(new Chunk(label + ": ", font(12, Font.NORMAL)));             // ← 일반체
    ph.add(new Chunk(value == null ? "" : value, font(12, Font.NORMAL))); // ← 일반체 + 파란색
    Paragraph p = new Paragraph(ph);
    p.setSpacingAfter(4f);
    return p;
  }
  private Paragraph kvLine(String label, long value, String suffix) {
    return kvLine(label, nf(value) + (suffix==null? "" : suffix));
  }
  private Paragraph kvLine(String label, double value, String suffix) {
    return kvLine(label, nfd(value) + (suffix==null? "" : suffix));
  }

  /** 섹션 제목 — 소제목만 볼드 */
  private Paragraph section(String title) {
    Paragraph p = new Paragraph(title, font(14, Font.BOLD)); // ← 소제목 볼드
    p.setSpacingBefore(10f);
    p.setSpacingAfter(6f);
    return p;
  }

  /** 랭킹 1~3위: "n위: 값(파란색)" — 전부 일반체, 값만 파란색 */
  private void addRankSimple(Document doc, List<String> values, String sectionTitle) throws Exception {
    doc.add(section(sectionTitle));
    for (int i=0; i<3; i++) {
      String v = (values != null && values.size()>i) ? values.get(i) : "";
      Phrase ph = new Phrase();
      ph.add(new Chunk((i+1) + "위: ", font(12, Font.NORMAL)));                 // ← 일반체
      ph.add(new Chunk(v == null ? "" : v, font(12, Font.NORMAL, BLUE)));       // ← 일반체 + 파란색
      Paragraph p = new Paragraph(ph); p.setSpacingAfter(2f);
      doc.add(p);
    }
  }

  /** 랭킹 1~3위(상품명 + (건수)) — 모두 일반체, 상품명만 파란색 */
  private void addRankItems(Document doc, String sectionTitle, List<AdminReport.TopItem> items, String unit) throws Exception {
    doc.add(section(sectionTitle));
    for (int i=0; i<3; i++) {
      String name = (items!=null && items.size()>i && items.get(i)!=null) ? (items.get(i).name==null? "" : items.get(i).name) : "";
      int count   = (items!=null && items.size()>i && items.get(i)!=null) ? items.get(i).count : 0;

      Phrase ph = new Phrase();
      ph.add(new Chunk((i+1) + "위 상품: ", font(12, Font.NORMAL)));           // ← 일반체
      ph.add(new Chunk(name, font(12, Font.NORMAL, BLUE)));                    // ← 일반체 + 파란색
      ph.add(new Chunk(" (" + nf(count) + unit + ")", font(12, Font.NORMAL))); // 괄호/단위 일반체

      Paragraph p = new Paragraph(ph); p.setSpacingAfter(2f);
      doc.add(p);
    }
  }

  /** 값만 크게(일반체) 파란색으로 단독 출력 (예: 4:6, 7:3) */
  private void addBigValue(Document doc, String sectionTitle, String value) throws Exception {
    doc.add(section(sectionTitle));
    Paragraph p = new Paragraph(value == null ? "" : value, font(16, Font.NORMAL)); // ← 일반체 + 파란색
    p.setSpacingAfter(8f);
    doc.add(p);
  }

  /* ========= 메인 ========= */
  public void write(AdminReport r, OutputStream os) throws Exception {
    ensureFont();
    Document doc = new Document(PageSize.A4, 36, 36, 36, 36);
    PdfWriter.getInstance(doc, os);
    doc.open();

    // 제목 — 메인 제목만 볼드
    Paragraph title = new Paragraph(String.format("%d년 %d월 리포트", r.ym.getYear(), r.ym.getMonthValue()), font(18, Font.BOLD));
    title.setSpacingAfter(14f);
    doc.add(title);

    // 신규/총 가입자
    doc.add(kvLine("신규 가입자 수", nf(r.newJoiners) + "명 " + nz(r.momChangeText)));
    doc.add(kvLine("총 가입자 수",  nf(r.totalJoiners) + "명"));

    // 연령대/성별 1~3위
    addRankSimple(doc, r.top3AgeGender, "연령대/성별");

    // 전체 성비
    addBigValue(doc, "전체 가입자 남녀 비율", r.genderRatioText);

    // 이번달 가입 TOP3
    addRankItems(doc, "이번달 가입", r.top3Joined, "건");

    // 총 조회수 TOP3
    addRankItems(doc, "총 조회수", r.top3Viewed, "건");

    // 평균 별점
    doc.add(section("평균 별점"));
    doc.add(kvLine("평균 별점", nfd(r.avgRating) + "점"));

    // 긍정/부정 비율
    addBigValue(doc, "긍정 부정 비율", r.sentimentRatioText);

    doc.close();
  }
}
