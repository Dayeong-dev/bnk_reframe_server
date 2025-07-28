package com.example.reframe.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="access_log")
@Data
public class AccessLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;				// 로그 고유번호
	
	@Column(name="ip_address")
	private String ipAddress;  		// 접속한 ip
	@Column(name="visit_date")
	private LocalDate visitDate;	// 접속한 날짜

}
