package com.example.reframe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.admin.CardApprovalRequest;

public interface CardApprovalRequestRepository extends JpaRepository<CardApprovalRequest, Long>{
	List<CardApprovalRequest> findByStatus(String status);

	List<CardApprovalRequest> findByRequestedBy(String username);

	List<CardApprovalRequest> findByRequestedByAndStatus(String username, String status);


}
