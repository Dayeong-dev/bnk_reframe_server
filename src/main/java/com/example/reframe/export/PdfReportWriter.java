package com.example.reframe.export;

import com.example.reframe.dto.report.AdminReport;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.OutputStream;
import java.net.URL;

public class PdfReportWriter {
  private volatile BaseFont baseFont;

  private synchronized void ensureFont() {
    if (baseFont != null) return;
    try {
      URL url = getClass().getResource("/fonts/NanumGothic-Regular.ttf");
      if (url == null) {
        throw new IllegalStateException("폰트 파일 없음: /fonts/NanumGothic-Regular.ttf");
      }
      // BaseFont 직접 생성
      baseFont = BaseFont.createFont(url.toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    } catch (Exception e) {
      throw new IllegalStateException("한글 폰트 등록 실패", e);
    }
  }

  private Font font(float size, int style) {
    ensureFont();
    return new Font(baseFont, size, style);
  }

  public void write(AdminReport r, OutputStream os) throws Exception {
    ensureFont();
    Document doc = new Document(PageSize.A4, 36, 36, 36, 36);
    PdfWriter.getInstance(doc, os);
    doc.open();

    Paragraph title = new Paragraph(
      String.format("%d년 %d월 리포트", r.ym.getYear(), r.ym.getMonthValue()),
      font(16, Font.BOLD)
    );
    title.setSpacingAfter(12);
    doc.add(title);

    // 이하 기존 내용 동일...
    doc.add(new Paragraph(
      String.format("신규 (상품)가입자 수: %,d명 %s", r.newJoiners, nz(r.momChangeText)),
      font(11, Font.NORMAL)
    ));
    // ... (중략)
    doc.close();
  }

  private static String nz(String s){ return (s==null || s.isBlank()) ? "" : s; }
}
