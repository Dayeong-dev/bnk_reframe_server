package com.example.reframe.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {

	@Value("${google.recaptcha.secret}")
	private String secretKey;

	private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

	public boolean verify(String recaptchaToken) {
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("secret", secretKey);
		params.add("response", recaptchaToken);

		ResponseEntity<Map> response = restTemplate.postForEntity(VERIFY_URL, params, Map.class);
		Map<String, Object> body = response.getBody();

		return body != null && (Boolean) body.get("success");
	}
}
