package com.example.reframe.entity.admin;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "APPROVAL_REQUEST_DETAIL")
@Getter
@Setter 
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApprovalRequestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id")
    @JsonBackReference
    private ApprovalRequest request;

    private String fieldName;	// 변경된 필드명
    
    @Lob
    private String oldValue;	// 수정 전 데이터
    
    @Lob
    private String newValue;	// 수정 후 데이터
}