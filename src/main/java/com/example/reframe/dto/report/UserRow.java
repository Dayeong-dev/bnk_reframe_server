package com.example.reframe.dto.report;

public record UserRow(
	    String username,
	    String email,
	    String phone,
	    String usertype, // P(개인)/C(기업)
	    String role
	) {}