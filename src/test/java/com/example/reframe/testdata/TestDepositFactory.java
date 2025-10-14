package com.example.reframe.testdata;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import com.example.reframe.entity.DepositProduct;
import com.example.reframe.repository.DepositProductRepository;

@SpringBootTest
public class TestDepositFactory {

	@Autowired
	private DepositProductRepository depositRepository;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	
	@Test
	/* 예적금 상세 정보 데이터 추가 */
	public void updateDepositDetailInfo() {
		List<DepositProduct> productList = depositRepository.findAll();
		
		for(DepositProduct product : productList) {
			String location = "classpath:static/deposit/detail/" + product.getProductId() + ".md";
	        Resource resource = resourceLoader.getResource(location);

	        if (!resource.exists() || !resource.isReadable()) {
	        	System.out.println("리소스를 찾을 수 없거나 읽을 수 없습니다: " + location);
	        	continue;
	        }
	        
	        try (InputStream in = resource.getInputStream()) {
	            String markdown = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
	            
	            product.setModalDetail(markdown);
	            depositRepository.save(product);
	        } catch (IOException e) {
	            System.out.println("읽기 중 오류: " + location + " - " + e.getMessage());
	        }

		}
	}
	
	@Test
	/* 예적금 금리 정보 데이터 추가 */
	public void updateDepositRateInfo() {
		List<DepositProduct> productList = depositRepository.findAll();
		
		for(DepositProduct product : productList) {
			String location = "classpath:static/deposit/rate/" + product.getProductId() + ".md";
	        Resource resource = resourceLoader.getResource(location);

	        if (!resource.exists() || !resource.isReadable()) {
	        	System.out.println("리소스를 찾을 수 없거나 읽을 수 없습니다: " + location);
	        	continue;
	        }
	        
	        try (InputStream in = resource.getInputStream()) {
	            String markdown = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
	            
	            product.setModalRate(markdown);
	            depositRepository.save(product);
	        } catch (IOException e) {
	            System.out.println("읽기 중 오류: " + location + " - " + e.getMessage());
	        }

		}
	}
	
}
