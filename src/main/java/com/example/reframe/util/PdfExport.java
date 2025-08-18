package com.example.reframe.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Function;

public final class PdfExport {
  private PdfExport(){}

  private static Font kFont(float size, int style) {
    try {
      InputStream is = PdfExport.class.getResourceAsStream("/fonts/NanumGothic.ttf");
      if (is != null) {
        byte[] buf = is.readAllBytes();
        BaseFont bf = BaseFont.createFont("NanumGothic", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, buf, null);
        return new Font(bf, size, style);
      }
    } catch (Exception ignore) {}
    try {
      BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
      return new Font(bf, size, style);
    } catch (Exception e) { throw new RuntimeException(e); }
  }

  public static <T> void write(String title, String[] headers, List<T> rows,
                               Function<T,List<String>> mapper, OutputStream os) {
    Document doc = new Document(PageSize.A4.rotate());
    try {
      PdfWriter.getInstance(doc, os);
      doc.open();
      Font titleFont = kFont(16, Font.BOLD);
      Font headerFont = kFont(10, Font.BOLD);
      Font cellFont   = kFont(10, Font.NORMAL);

      doc.add(new Paragraph(title, titleFont));
      doc.add(Chunk.NEWLINE);

      PdfPTable table = new PdfPTable(headers.length);
      table.setWidthPercentage(100);

      for (String h : headers) table.addCell(new Phrase(h, headerFont));
      for (T row : rows) for (String s : mapper.apply(row))
        table.addCell(new Phrase(s == null ? "" : s, cellFont));

      doc.add(table);
    } catch (Exception e) { throw new RuntimeException(e);
    } finally { doc.close(); }
  }
}
