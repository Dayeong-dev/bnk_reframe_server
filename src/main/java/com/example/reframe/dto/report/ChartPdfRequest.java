package com.example.reframe.dto.report;

import java.util.Map;

import lombok.Data;

@Data
public class ChartPdfRequest {
  private int year;
  private int month;
  private Map<String, String> images; // key: 'sentimentChart' | 'genderChart' / value: base64 PNG
}
