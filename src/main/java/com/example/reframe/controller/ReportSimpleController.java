package com.example.reframe.controller;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// ---- Excel (POI) : 충돌 방지 위해 필요한 것만 import ----
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

@Controller
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class ReportSimpleController {

    // =========================
    // 1) PDF
    // =========================
    @GetMapping("/simple")
    public ResponseEntity<byte[]> downloadSimple(
            @RequestParam(name = "from", required = false) String fromStr,
            @RequestParam(name = "to", required = false) String toStr
    ) {
        try {
            LocalDate to   = (toStr   == null ? LocalDate.now() : LocalDate.parse(toStr));
            LocalDate from = (fromStr == null ? to.minusDays(29) : LocalDate.parse(fromStr));
            String period  = from.format(DateTimeFormatter.ISO_DATE) + " ~ " + to.format(DateTimeFormatter.ISO_DATE);

            byte[] pdf = buildPdf(period);

            String filename = "BNK 관리자 리포트_" + to + ".pdf";
            String cd = "attachment; filename*=UTF-8''" + URLEncoder.encode(filename, StandardCharsets.UTF_8);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, cd)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            String msg = "Report generation failed: " + e.getMessage();
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(msg.getBytes(StandardCharsets.UTF_8));
        }
    }

    // =========================
    // 2) EXCEL
    // =========================
    @GetMapping({"/simple.xlsx", "/simple-excel"})
    public ResponseEntity<byte[]> downloadSimpleExcel(
            @RequestParam(name = "from", required = false) String fromStr,
            @RequestParam(name = "to", required = false) String toStr
    ) {
        try {
            LocalDate to   = (toStr   == null ? LocalDate.now() : LocalDate.parse(toStr));
            LocalDate from = (fromStr == null ? to.minusDays(29) : LocalDate.parse(fromStr));
            String period  = from.format(DateTimeFormatter.ISO_DATE) + " ~ " + to.format(DateTimeFormatter.ISO_DATE);

            byte[] xlsx = buildExcel(period);

            String filename = "BNK 관리자 리포트_" + to + ".xlsx";
            String cd = "attachment; filename*=UTF-8''" + URLEncoder.encode(filename, StandardCharsets.UTF_8);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, cd)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(xlsx);

        } catch (Exception e) {
            String msg = "Excel generation failed: " + e.getMessage();
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(msg.getBytes(StandardCharsets.UTF_8));
        }
    }

    // =========================
    // PDF 생성
    // =========================
    private byte[] buildPdf(String period) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 36, 36, 36, 36);
        PdfWriter writer = PdfWriter.getInstance(doc, baos);
        doc.addTitle("BNK Dashboard Snapshot");
        doc.addAuthor("BNK Admin");
        doc.addSubject("Dashboard-like PDF");
        doc.open();

        BaseFont bf = loadNanumGothic(); // 한글 폰트 (없으면 HELVETICA)

        com.lowagie.text.Font H1 = new com.lowagie.text.Font(bf, 16, com.lowagie.text.Font.BOLD);
        com.lowagie.text.Font H2 = new com.lowagie.text.Font(bf, 13, com.lowagie.text.Font.BOLD);
        com.lowagie.text.Font P  = new com.lowagie.text.Font(bf, 11);

        Paragraph title = new Paragraph("BNK 관리자 리포트", H1);
        title.setSpacingAfter(6f);
        doc.add(title);
        doc.add(new Paragraph("기간: " + period, P));
        doc.add(new Paragraph(" ", P));

        // 0) KPI
        addH2(doc, H2, "핵심지표");
        String[][] kpiRows = {
                {"금일 방문자 수","280","어제 대비 ▼ 84.1%"},
                {"푸시 알림 클릭율","72%","전월 대비 ▲ 10.3%"},
                {"최근 7일 가입","66","전일 대비 ▼ 18.2%"},
                {"활성 템플릿","2","자동 발송 설정"}
        };
        addTable(doc, bf, new String[]{"항목","값","메모"}, kpiRows, new float[]{2,1,2});
        doc.add(Spacer(P, 8f));

        // 1) Step 이탈율
        addH2(doc, H2, "가입 프로세스 이탈율");
        String[][] steps = {
                {"1","약관동의","21.9%","78.1%"},
                {"2","본인인증","32.4%","67.6%"},
                {"3","정보입력","10.0%","90.0%"}
        };
        addTable(doc, bf, new String[]{"Step","단계","이탈율","유지율"}, steps, new float[]{1,2,1,1});
        doc.add(Spacer(P, 8f));

        // 2) 월별 추세
        addH2(doc, H2, "상품가입 추세 (월별)");
        String[] months = {"1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"};
        int[] monthVals = {510,200,350,40,500,62,721,822,955,100,111,12};
        String[][] trendRows = new String[months.length][2];
        for (int i=0;i<months.length;i++) { trendRows[i][0]=months[i]; trendRows[i][1]=String.valueOf(monthVals[i]); }
        addTable(doc, bf, new String[]{"월","가입 수"}, trendRows, new float[]{2,1});

        doc.newPage();

        // 3) 일일 방문자
        addH2(doc, H2, "일일 방문자 수");
        String[][] dailyRows = {
            {"2025-08-15","213"},{"2025-08-16","120"},{"2025-08-17","1,198"},{"2025-08-18","1,440"},
            {"2025-08-19","1,566"},{"2025-08-20","1,602"},{"2025-08-21","1,530"},{"2025-08-22","1,590"},
            {"2025-08-23","307"},{"2025-08-24","1,688"},{"2025-08-25","1,745"},{"2025-08-26","1,801"},
            {"2025-08-27","1,920"},{"2025-08-28","280"}
        };
        addTable(doc, bf, new String[]{"일자","방문자"}, dailyRows, new float[]{2,1});
        doc.add(Spacer(P, 8f));

        // 4) 리뷰 분포
        addH2(doc, H2, "상품가입 후기 분포");
        int pos = 671, neg = 329; int sum = Math.max(1, pos+neg);
        String[][] reviewRows = {
                {"긍정", String.valueOf(pos), String.format("%.1f%%", 100.0 * pos / sum)},
                {"부정", String.valueOf(neg), String.format("%.1f%%", 100.0 * neg / sum)}
        };
        addTable(doc, bf, new String[]{"구분","건수","비율"}, reviewRows, new float[]{2,1,1});
        doc.add(Spacer(P, 8f));

        // 5) 상품 조회수 Top5
        addH2(doc, H2, "상품 조회수 TOP 5");
        String[][] top5Rows = {
            {"매일 출석 적금","175"},
            {"함께 걷는 적금","172"},
            {"오징어 모임 통장","59"},
            {"BNK 내맘대로 예금","51"},
            {"사장님 월급통장","42"}
        };
        addTable(doc, bf, new String[]{"상품명","조회수"}, top5Rows, new float[]{3,1});

        doc.newPage();

        // 6) 결재목록
        addH2(doc, H2, "결재목록");
        String[][] apprRows = {
                {"2025.08.25 09:54","대기","김법진","관리자 대시보드 색상 및 디자인 수정요청"},
                {"2025.08.22 12:22","반려","관리자","【긴급】부산은행 로고디자인 변경 결재요청"},
                {"2025.08.14 13:33","승인","김법진","광복 80주년 기념 특별 이벤트 행사 주최 결재요청"}
        };
        addTable(doc, bf, new String[]{"일시","상태","요청자","제목"}, apprRows, new float[]{2,1,1,3});
        doc.add(Spacer(P, 8f));

        // 7) 활성 템플릿
        addH2(doc, H2, "활성 템플릿");
        String[][] fcmRows = {
                {"ALL","0 0 9 * * *","부산은행과 함께 민생회복받고 지원금도 함께 받아가요 ~"},
                {"ALL","0 0 9 * * *","전국 러너들 주목🏃 걷기만 해도 우대금리를 주는 \"함께 걷는 적금\" 출시🎉 내 우대금리 확인 >>> "}
        };
        addTable(doc, bf, new String[]{"그룹코드","CRON","본문"}, fcmRows, new float[]{2,2,5});

        doc.add(Spacer(P, 10f));
        Paragraph foot = new Paragraph("※ 본 레포트는 대시보드 요약을 기반으로 자동 생성된 미리보기입니다.", new com.lowagie.text.Font(bf, 9));
        foot.setAlignment(Element.ALIGN_LEFT);
        doc.add(foot);

        doc.close();
        return baos.toByteArray();
    }

    // =========================
    // Excel 생성
    // =========================
    private byte[] buildExcel(String period) throws Exception {
        try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // ---- Styles (POI 전용 Font/Style 사용) ----
            CellStyle title = wb.createCellStyle();
            org.apache.poi.ss.usermodel.Font fTitle = wb.createFont();
            fTitle.setBold(true);
            fTitle.setFontHeightInPoints((short)14);
            title.setFont(fTitle);

            CellStyle h = wb.createCellStyle();
            org.apache.poi.ss.usermodel.Font fH = wb.createFont();
            fH.setBold(true);
            h.setFont(fH);
            h.setAlignment(HorizontalAlignment.CENTER);
            h.setVerticalAlignment(VerticalAlignment.CENTER);
            h.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            h.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            h.setBorderTop(BorderStyle.THIN); h.setBorderBottom(BorderStyle.THIN);
            h.setBorderLeft(BorderStyle.THIN); h.setBorderRight(BorderStyle.THIN);

            CellStyle c = wb.createCellStyle();
            c.setAlignment(HorizontalAlignment.CENTER);
            c.setVerticalAlignment(VerticalAlignment.CENTER);
            c.setBorderTop(BorderStyle.THIN); c.setBorderBottom(BorderStyle.THIN);
            c.setBorderLeft(BorderStyle.THIN); c.setBorderRight(BorderStyle.THIN);

            CellStyle left = wb.createCellStyle();
            left.cloneStyleFrom(c);
            left.setAlignment(HorizontalAlignment.LEFT);

            CellStyle pct = wb.createCellStyle();
            pct.cloneStyleFrom(c);
            pct.setDataFormat(wb.createDataFormat().getFormat("0.0%"));

            // ---- Sheets ----
            int row;

            // Sheet 1: KPI & Steps
            Sheet s1 = wb.createSheet("핵심지표 & 이탈율");
            row = 0; row = writeTitle(s1, row, "BNK 관리자 리포트 (Excel)", title);
            row = writeLine(s1, row, "기간: " + period);

            // KPI
            row = writeSection(s1, row, "핵심지표");
            writeTable(s1, row, new String[]{"항목","값","메모"},
                    new String[][]{
                            {"금일 방문자 수","280","어제 대비 ▼ 84.1%"},
                            {"푸시 알림 클릭율","72%","전월 대비 ▲ 10.3%"},
                            {"최근 7일 가입","66","전일 대비 ▼ 18.2%"},
                            {"활성 템플릿","2","자동 발송 설정"}
                    }, h, c, left, pct);
            row += 1 + 1 + 4; // section + header + rows
            row += 1;

            // Steps
            row = writeSection(s1, row, "가입 프로세스 이탈율");
            writeTable(s1, row, new String[]{"Step","단계","이탈율","유지율"},
                    new String[][]{
                            {"1","약관동의","21.9%","78.1%"},
                            {"2","본인인증","32.4%","67.6%"},
                            {"3","정보입력","10.0%","90.0%"}
                    }, h, c, left, pct);
            row += 1 + 1 + 3;
            autosize(s1, 4);

            // Sheet 2: 월별 추세
            Sheet s2 = wb.createSheet("월별 추세");
            row = 0; row = writeTitle(s2, row, "상품가입 추세 (월별)", title);
            row = writeLine(s2, row, "기간: " + period);
            writeTable(s2, row, new String[]{"월","가입 수"}, makeMonthlyRows(), h, c, left, pct);
            autosize(s2, 2);

            // Sheet 3: 일일 방문자
            Sheet s3 = wb.createSheet("일일 방문자");
            row = 0; row = writeTitle(s3, row, "일일 방문자 수", title);
            row = writeLine(s3, row, "기간: " + period);
            writeTable(s3, row, new String[]{"일자","방문자"},
                    new String[][]{
                            {"2025-08-15","213"},{"2025-08-16","120"},{"2025-08-17","1198"},{"2025-08-18","1440"},
                            {"2025-08-19","1566"},{"2025-08-20","1602"},{"2025-08-21","1530"},{"2025-08-22","1590"},
                            {"2025-08-23","307"},{"2025-08-24","1688"},{"2025-08-25","1745"},{"2025-08-26","1801"},
                            {"2025-08-27","1920"},{"2025-08-28","280"}
                    }, h, c, left, pct);
            autosize(s3, 2);

            // Sheet 4: 리뷰 분포
            Sheet s4 = wb.createSheet("리뷰 분포");
            row = 0; row = writeTitle(s4, row, "상품가입 후기 분포", title);
            row = writeLine(s4, row, "기간: " + period);
            writeTable(s4, row, new String[]{"구분","건수","비율"},
                    new String[][]{
                            {"긍정","671","67.1%"},
                            {"부정","329","32.9%"}
                    }, h, c, left, pct);
            autosize(s4, 3);

            // Sheet 5: 상품 조회수 TOP5
            Sheet s5 = wb.createSheet("상품 조회수 TOP5");
            row = 0; row = writeTitle(s5, row, "상품 조회수 TOP 5", title);
            row = writeLine(s5, row, "기간: " + period);
            writeTable(s5, row, new String[]{"상품명","조회수"},
                    new String[][]{
                            {"매일 출석 적금","175"},
                            {"함께 걷는 적금","172"},
                            {"오징어 모임 통장","59"},
                            {"BNK 내맘대로 예금","51"},
                            {"사장님 월급통장","42"}
                    }, h, c, left, pct);
            autosize(s5, 2);

            // Sheet 6: 결재목록
            Sheet s6 = wb.createSheet("결재목록");
            row = 0; row = writeTitle(s6, row, "결재목록", title);
            row = writeLine(s6, row, "기간: " + period);
            writeTable(s6, row, new String[]{"일시","상태","요청자","제목"},
                    new String[][]{
                            {"2025.08.25 09:54","대기","김법진","관리자 대시보드 색상 및 디자인 수정요청"},
                            {"2025.08.22 12:22","반려","관리자","【긴급】부산은행 로고디자인 변경 결재요청"},
                            {"2025.08.14 13:33","승인","김법진","광복 80주년 기념 특별 이벤트 행사 주최 결재요청"}
                    }, h, c, left, pct);
            autosize(s6, 4);

            // Sheet 7: 활성 템플릿
            Sheet s7 = wb.createSheet("활성 템플릿");
            row = 0; row = writeTitle(s7, row, "활성 템플릿", title);
            row = writeLine(s7, row, "기간: " + period);
            writeTable(s7, row, new String[]{"그룹코드","CRON","본문"},
                    new String[][]{
                            {"ALL","0 0 9 * * *","부산은행과 함께 민생회복받고 지원금도 함께 받아가요 ~"},
                            {"ALL","0 0 9 * * *","전국 러너들 주목🏃 걷기만 해도 우대금리를 주는 \"함께 걷는 적금\" 출시🎉 내 우대금리 확인 >>> "}
                    }, h, c, left, pct);
            autosize(s7, 3);

            wb.write(baos);
            return baos.toByteArray();
        }
    }

    // ========= Excel helpers (POI 전용 타입 명시) =========
    private int writeTitle(Sheet s, int row, String text, CellStyle title) {
        Row r = s.createRow(row++);
        Cell c = r.createCell(0);
        c.setCellValue(text);
        c.setCellStyle(title);
        return row;
    }
    private int writeLine(Sheet s, int row, String text) {
        Row r = s.createRow(row++);
        Cell c = r.createCell(0);
        c.setCellValue(text);
        row++; // 빈 줄
        return row;
    }
    private int writeSection(Sheet s, int row, String name) {
        Row r = s.createRow(row++);
        Cell c = r.createCell(0);
        c.setCellValue("■ " + name);
        return row;
    }

    private void writeTable(Sheet s, int startRow, String[] headers, String[][] rows,
                            CellStyle h, CellStyle c, CellStyle left, CellStyle pct) {
        int r = startRow;

        // header
        Row hr = s.createRow(r++);
        for (int i = 0; i < headers.length; i++) {
            Cell hc = hr.createCell(i);
            hc.setCellValue(headers[i]);
            hc.setCellStyle(h);
        }

        // body
        if (rows != null) {
            for (String[] row : rows) {
                Row br = s.createRow(r++);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = br.createCell(i);
                    String v = (row != null && i < row.length && row[i] != null) ? row[i] : "";
                    // 퍼센트/숫자 간단 포맷
                    if (v.endsWith("%")) {
                        try {
                            double d = Double.parseDouble(v.replace("%","").trim()) / 100.0;
                            cell.setCellValue(d);
                            cell.setCellStyle(pct);
                        } catch (NumberFormatException e) {
                            cell.setCellValue(v);
                            cell.setCellStyle(i == headers.length-1 ? left : c);
                        }
                    } else if (v.matches("^-?\\d{1,3}(,\\d{3})*$")) { // 1,234
                        cell.setCellValue(Long.parseLong(v.replace(",", "")));
                        cell.setCellStyle(c);
                    } else if (v.matches("^-?\\d+(\\.\\d+)?$")) {       // 123 or 12.3
                        cell.setCellValue(Double.parseDouble(v));
                        cell.setCellStyle(c);
                    } else {
                        cell.setCellValue(v);
                        cell.setCellStyle(i == headers.length-1 ? left : c);
                    }
                }
            }
        }
    }

    private void autosize(Sheet s, int cols) {
        for (int i = 0; i < cols; i++) s.autoSizeColumn(i);
    }

    private String[][] makeMonthlyRows() {
        String[] months = {"1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"};
        int[] vals = {510,200,350,40,500,62,721,822,955,100,111,12};
        String[][] rows = new String[months.length][2];
        for (int i=0;i<months.length;i++) { rows[i][0]=months[i]; rows[i][1]=String.valueOf(vals[i]); }
        return rows;
    }

    // =========================
    // PDF 유틸
    // =========================
    private void addH2(Document doc, com.lowagie.text.Font h2, String text) throws com.lowagie.text.DocumentException {
        Paragraph p = new Paragraph(text, h2);
        p.setSpacingBefore(4f);
        p.setSpacingAfter(6f);
        doc.add(p);
    }

    private Paragraph Spacer(com.lowagie.text.Font f, float space) {
        Paragraph p = new Paragraph(" ", f);
        p.setSpacingAfter(space);
        return p;
    }

    private void addTable(Document doc, BaseFont bf, String[] headers, String[][] rows, float[] widths)
            throws com.lowagie.text.DocumentException {
        PdfPTable t = new PdfPTable(headers.length);
        t.setWidthPercentage(100);
        if (widths != null) t.setWidths(widths);
        t.getDefaultCell().setPadding(6f);
        for (String h : headers) t.addCell(headerCell(h, bf));
        if (rows != null) {
            for (String[] r : rows) {
                for (int i = 0; i < headers.length; i++) {
                    String v = (r != null && i < r.length && r[i] != null) ? r[i] : "";
                    t.addCell(bodyCell(v, bf, i == headers.length - 1));
                }
            }
        }
        doc.add(t);
    }

    private PdfPCell headerCell(String text, BaseFont bf) {
        PdfPCell c = new PdfPCell(new Paragraph(text, new com.lowagie.text.Font(bf, 11, com.lowagie.text.Font.BOLD)));
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
        c.setGrayFill(0.92f);
        c.setPadding(6f);
        return c;
    }

    private PdfPCell bodyCell(String text, BaseFont bf, boolean leftAlign) {
        PdfPCell c = new PdfPCell(new Paragraph(text, new com.lowagie.text.Font(bf, 11)));
        c.setHorizontalAlignment(leftAlign ? Element.ALIGN_LEFT : Element.ALIGN_CENTER);
        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
        c.setPadding(6f);
        return c;
    }

    // NanumGothic-Regular.ttf: src/main/resources/fonts/NanumGothic-Regular.ttf
    private BaseFont loadNanumGothic() throws Exception {
        try (var is = getClass().getResourceAsStream("/fonts/NanumGothic-Regular.ttf")) {
//            if (is == null) return BaseFont.HELVETICA;
            byte[] bytes = is.readAllBytes();
            return BaseFont.createFont(
                    "NanumGothic-Regular.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED,
                    true,
                    bytes,
                    null
            );
        }
    }
}
