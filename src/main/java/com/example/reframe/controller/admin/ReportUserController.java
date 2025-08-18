package com.example.reframe.controller.admin;

import com.example.reframe.dto.report.UserFilter;
import com.example.reframe.service.ReportUserService;
import com.example.reframe.util.ExcelExport;
import com.example.reframe.util.PdfExport;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/admin/reports")
public class ReportUserController {

  private final ReportUserService service;
  private static final ZoneId KST = ZoneId.of("Asia/Seoul");
  private static final String[] HEADERS = {"아이디","이메일","전화번호","회원타입","역할"};

  public ReportUserController(ReportUserService service) { this.service = service; }

  @GetMapping({"/users", "/subscribers"})
  public ResponseEntity<?> downloadUsers(
      @RequestParam(name="keyword",  required=false) String keyword,
      @RequestParam(name="usertype", required=false) String usertype,
      @RequestParam(name="role",     required=false) String role,
      @RequestParam(name="format",   defaultValue="xlsx") String format
  ){
    try {
      var rows = service.getRows(new UserFilter(keyword, usertype, role));

      String ts = ZonedDateTime.now(KST).format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
      String base = "users_" + ts;
      boolean isPdf = "pdf".equalsIgnoreCase(format);
      String ext = isPdf ? "pdf" : "xlsx";

      var baos = new java.io.ByteArrayOutputStream();
      if (isPdf) {
        PdfExport.write("가입자(사용자) 리포트", HEADERS, rows,
            r -> java.util.List.of(r.username(), r.email(), r.phone(), r.usertype(), r.role()), baos);
      } else {
        ExcelExport.write("Users", HEADERS, rows,
            r -> java.util.List.of(r.username(), r.email(), r.phone(), r.usertype(), r.role()), baos);
      }
      byte[] bytes = baos.toByteArray();

      HttpHeaders headers = new HttpHeaders();
      headers.set("X-Content-Type-Options","nosniff");
      headers.setCacheControl(CacheControl.noStore().getHeaderValue());
      String filename = base + "." + ext;
      String encoded  = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+","%20");
      headers.add(HttpHeaders.CONTENT_DISPOSITION,
          "attachment; filename=\"" + filename + "\"; filename*=UTF-8''" + encoded);
      headers.setContentLength(bytes.length);
      headers.setContentType(isPdf ? MediaType.APPLICATION_PDF :
          MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

      return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(500)
          .contentType(MediaType.TEXT_PLAIN)
          .body("다운로드 실패: " + e.getClass().getSimpleName() + " - " + String.valueOf(e.getMessage()));
    }
  }
}
