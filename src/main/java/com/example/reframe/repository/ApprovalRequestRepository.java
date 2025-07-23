package com.example.reframe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.admin.ApprovalRequest;

public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest,Long>{

	List<ApprovalRequest> findByStatus(String string);

	List<ApprovalRequest> findByRequestedBy(String username);

	List<ApprovalRequest> findByRequestedByAndStatus(String username, String status);

}
