package com.example.reframe.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BusinessApiResponse {
	@JsonProperty("data")
	private List<BusinessApiResult> data;
}
