package com.example.reframe.service;

import com.example.reframe.dto.report.UserFilter;
import com.example.reframe.dto.report.UserRow;
import com.example.reframe.repository.ReportUserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportUserService {
	private final ReportUserRepository repo;

	public ReportUserService(ReportUserRepository repo) { this.repo = repo; }

	public List<UserRow> getRows(UserFilter f) {
		return repo.find(f);
	}
}
