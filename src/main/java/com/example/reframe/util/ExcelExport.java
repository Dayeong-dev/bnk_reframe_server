package com.example.reframe.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public final class ExcelExport {
  private ExcelExport(){}
  public static <T> void write(String sheetName, String[] headers, List<T> rows,
                               Function<T,List<Object>> mapper, OutputStream os) throws Exception {
    try (var wb = new XSSFWorkbook()) {
      Sheet sheet = wb.createSheet(sheetName);
      int r = 0;
      Row header = sheet.createRow(r++);
      for (int i = 0; i < headers.length; i++) header.createCell(i).setCellValue(headers[i]);
      for (T row : rows) {
        var data = mapper.apply(row);
        Row rr = sheet.createRow(r++);
        for (int c = 0; c < data.size(); c++) {
          var cell = rr.createCell(c);
          Object v = data.get(c);
          if (v == null) cell.setBlank();
          else if (v instanceof Number n) cell.setCellValue(n.doubleValue());
          else if (v instanceof LocalDateTime dt) cell.setCellValue(java.sql.Timestamp.valueOf(dt));
          else cell.setCellValue(String.valueOf(v));
        }
      }
      for (int i=0;i<headers.length;i++) sheet.autoSizeColumn(i);
      wb.write(os);
    }
  }
}
