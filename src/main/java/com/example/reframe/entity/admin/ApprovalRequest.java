package com.example.reframe.entity.admin;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="APPROVAL_REQUEST")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ApprovalRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;			// 고유 ID

	private Long productId;		// 상품 ID
	
	private String requestedBy; // 예: 관리자 ID

    private String status; 		// 상태 ( PENDING, APPROVED, REJECTED ) 

    private String approvedBy;	// 승인한 상위관리자 ID

    private LocalDateTime requestedAt;  // 요청시간

    private LocalDateTime approvedAt;	// 승인시간

    private String rejectionReason;		// 반려사유

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ApprovalRequestDetail> details;
	
	
}
