package com.example.reframe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.admin.CardApprovalRequest;

public interface CardApprovalRequestRepository extends JpaRepository<CardApprovalRequest, Long>{
	List<CardApprovalRequest> findByStatus(String status);

}
