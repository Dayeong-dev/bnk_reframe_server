package com.example.reframe.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reframe.repository.TestResultRepository;

@Service
public class TestResultService {

	@Autowired
	private TestResultRepository testResultRepository;
	
	public Map<String, Long> getTestResultStats() {
        List<Object[]> result = testResultRepository.findResultTypeCounts();
        Map<String, Long> stats = new LinkedHashMap<>(); 

        for (Object[] row : result) {
            String type = (String) row[0];
            Long count = (Long) row[1]; 
            stats.put(type, count);
        }

        return stats;
    }

	
}
