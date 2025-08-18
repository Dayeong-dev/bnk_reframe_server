package com.example.reframe.dto.report;

public record UserFilter(
	    String keyword,  // username/email/phone 부분검색
	    String usertype, // P or C
	    String role      // ROLE_*
	) {}
