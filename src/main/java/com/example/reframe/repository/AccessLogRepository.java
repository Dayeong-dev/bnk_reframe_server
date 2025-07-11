package com.example.reframe.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.reframe.entity.AccessLog;

public interface AccessLogRepository extends JpaRepository<AccessLog,Long>{

	@Query("SELECT COUNT(DISTINCT a.ipAddress) "
		 + "FROM AccessLog a "
		 + "WHERE a.visitDate = :today")
	long countTodayVistors(@Param("today") LocalDate today);

	boolean existsByIpAddressAndVisitDate(String ipAddress, LocalDate visitDate);
	 
	 
	@Query("SELECT a.visitDate, COUNT(DISTINCT a.ipAddress) " 
	      +"FROM AccessLog a "
	      +"GROUP BY a.visitDate "
	      +"ORDER BY a.visitDate ASC")
	List<Object[]> getDailyVisitStats();
}
