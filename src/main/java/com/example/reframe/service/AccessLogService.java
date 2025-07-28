package com.example.reframe.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reframe.dto.DailyVisitDTO;
import com.example.reframe.entity.AccessLog;
import com.example.reframe.repository.AccessLogRepository;

@Service
public class AccessLogService {

	@Autowired
	private AccessLogRepository accessLogRepository;

	public void logVistor(String ipAddress) {
		AccessLog log = new AccessLog();
		log.setIpAddress(ipAddress);
		log.setVisitDate(LocalDate.now());
		accessLogRepository.save(log);

	}

	public long getTodayVistorCount() {
		return accessLogRepository.countTodayVistors(LocalDate.now());
	}

	public boolean isAlreadyVisited(String ipAddress) {
		return accessLogRepository.existsByIpAddressAndVisitDate(ipAddress, LocalDate.now());
	}

	public void logVisitorOnce(String ipAddress) {
		LocalDate today = LocalDate.now();

		boolean alreadyLogged = accessLogRepository.existsByIpAddressAndVisitDate(ipAddress, today);

		if (!alreadyLogged) {
			AccessLog log = new AccessLog();
			log.setIpAddress(ipAddress);
			log.setVisitDate(today);
			accessLogRepository.save(log);
		}
	}
	
	public List<DailyVisitDTO> getDailyStats() {
        List<Object[]> rawList = accessLogRepository.getDailyVisitStats();

        List<DailyVisitDTO> result = new ArrayList<>();
        for (Object[] obj : rawList) {
            String date = obj[0].toString();
            Long count = (Long) obj[1];

            DailyVisitDTO dto = new DailyVisitDTO(date, count);
            result.add(dto);
        }

        return result;
    }

}
