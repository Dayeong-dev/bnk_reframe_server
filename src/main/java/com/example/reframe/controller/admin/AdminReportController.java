package com.example.reframe.controller.admin;

import com.example.reframe.dto.report.AdminReport;
import com.example.reframe.dto.report.ChartPdfRequest;
import com.example.reframe.export.ExcelReportWriter;
import com.example.reframe.export.PdfReportWriter;
import com.example.reframe.service.admin.ReportService;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
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

    /* ========== 📄 GET: PDF (텍스트만) ========== */
    @GetMapping("/monthly.pdf")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam("year") int year,
                                              @RequestParam("month") int month) {
        YearMonth ym = YearMonth.of(year, month);
        AdminReport r = reportService.buildMonthly(ym);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(32 * 1024)) {
            pdfWriter.write(r, baos); // 기존 텍스트-only 버전
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
                    .body(("PDF 생성 오류: " + e.getMessage()).getBytes(StandardCharsets.UTF_8));
        }
    }

    /* ========== 📄 POST: PDF (차트 이미지 포함) ========== */
    @PostMapping(
        value = "/monthly.pdf",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> monthlyPdfWithCharts(@RequestBody ChartPdfRequest req) {
        try {
            YearMonth ym = YearMonth.of(req.getYear(), req.getMonth());
            AdminReport r = reportService.buildMonthly(ym); // ✅ 올바른 서비스 사용

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream(64 * 1024)) {
                // 프론트에서 보낸 base64 PNG 맵을 사용하는 오버로드
                pdfWriter.write(r, req.getImages(), baos);
                byte[] bytes = baos.toByteArray();

                String filename = enc(String.format("관리자리포트_%d-%02d.pdf", req.getYear(), req.getMonth()));
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + filename)
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(bytes);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(("PDF(차트) 생성 오류: " + e.getMessage()).getBytes(StandardCharsets.UTF_8));
        }
    }

    /* ========== 📊 GET: Excel ========== */
    @GetMapping("/monthly.xlsx")
    public ResponseEntity<byte[]> downloadXlsx(@RequestParam("year") int year,
                                               @RequestParam("month") int month) {
        YearMonth ym = YearMonth.of(year, month);
        AdminReport r = reportService.buildMonthly(ym);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(32 * 1024)) {
            excelWriter.write(r, baos);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(("엑셀 생성 오류: " + e.getMessage()).getBytes(StandardCharsets.UTF_8));
        }
    }

    private static String enc(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    }
}
