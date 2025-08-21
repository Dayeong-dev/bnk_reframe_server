package com.example.reframe.controller.admin;

import com.example.reframe.dto.report.AdminReport;
import com.example.reframe.export.ExcelReportWriter;
import com.example.reframe.export.PdfReportWriter;
import com.example.reframe.service.ReportService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;

@Controller
@RequestMapping("/admin/reports")
public class AdminReportController {

    private final ReportService reportService;
    private final PdfReportWriter pdfWriter = new PdfReportWriter();
    private final ExcelReportWriter excelWriter = new ExcelReportWriter();

    public AdminReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // 📄 PDF 다운로드
    @GetMapping("/monthly.pdf")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam("year") int year, @RequestParam("month") int month) {
        YearMonth ym = YearMonth.of(year, month);
        AdminReport r = reportService.buildMonthly(ym);

        try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream(32 * 1024)) {
            pdfWriter.write(r, baos);                              // ✅ 먼저 메모리에 생성
            byte[] bytes = baos.toByteArray();

            String filename = enc(String.format("관리자리포트_%d-%02d.pdf", year, month));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + filename)
                    .contentLength(bytes.length)
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(("PDF 생성 오류: " + e.getMessage()).getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
    }

    // 📊 Excel 다운로드
    @GetMapping("/monthly.xlsx")
    public ResponseEntity<byte[]> downloadXlsx(@RequestParam("year") int year, @RequestParam("month") int month) {
        YearMonth ym = YearMonth.of(year, month);
        AdminReport r = reportService.buildMonthly(ym);

        try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream(32 * 1024)) {
            excelWriter.write(r, baos);                            // ✅ 먼저 메모리에 생성
            byte[] bytes = baos.toByteArray();

            String filename = enc(String.format("관리자리포트_%d-%02d.xlsx", year, month));
            MediaType xlsx = MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            return ResponseEntity.ok()
                    .contentType(xlsx)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + filename)
                    .contentLength(bytes.length)
                    .body(bytes);
        } catch (Exception e) {
            // 실패 시엔 파일 대신 500 + 메시지 (엑셀이 ‘손상’으로 오해하지 않도록)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(("엑셀 생성 오류: " + e.getMessage()).getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
    }
    private static String enc(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    }
}
