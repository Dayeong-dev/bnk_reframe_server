package com.example.reframe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.reframe.dto.BusinessApiResult;
import com.example.reframe.dto.BusinessApiResponse;
import com.example.reframe.dto.CorporateUserDTO;

@Service
public class PublicApiService {
	
	@Value("${public.api.biz.service-key}")
	private String biz_service_key;
	
	private final String BUSINESS_API_URL = "https://api.odcloud.kr/api/nts-businessman/v1";
	private final RestTemplate restTemplate = new RestTemplate();
	
	public boolean checkCorporateValidate(CorporateUserDTO corporateUserDTO) {
		String url = BUSINESS_API_URL + "/validate?serviceKey=" + biz_service_key;
		
		Map<String, Object> body = new HashMap<>();
		Map<String, String> data = new HashMap<>();
		
		data.put("b_no", corporateUserDTO.getBusinessNumber());
		data.put("start_dt", corporateUserDTO.getBusinessStartDate());
		data.put("p_nm", corporateUserDTO.getCeoName());
		data.put("p_nm2", "");
		data.put("b_nm", "");
		data.put("corp_no", "");
		data.put("b_sector", "");
		data.put("b_type", "");
		data.put("b_adr", "");
		
		List<Map<String, String>> list = List.of(data);
		body.put("businesses", list);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        
        try {
            ResponseEntity<BusinessApiResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, BusinessApiResponse.class);

            List<BusinessApiResult> resultList = response.getBody().getData();
            
            BusinessApiResult result =  resultList.get(0);
            
            
            if(result.getValid().equals("01")) {	// 유효한 사업자등록번호
            	System.out.println("유효한 사업자등록번호 입니다. ");
            	return true;
            }
            
            System.out.println("유효하지 않은 사업자등록번호 입니다. ");
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("사업자등록번호 진위확인 중 문제가 발생했습니다. ");
        }
        
        return false;
		
	}
}
