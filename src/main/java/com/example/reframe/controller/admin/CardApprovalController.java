package com.example.reframe.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.CardApprovalRequestDTO;
import com.example.reframe.dto.CardDto;
import com.example.reframe.entity.Card;
import com.example.reframe.service.CardApprovalService;
import com.example.reframe.util.SessionUtil;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin/card/approval")
public class CardApprovalController {

	 @Autowired
	    private CardApprovalService cardApprovalService;

	    // 수정 결재 요청 생성
	    @PostMapping("/request")
	    public ResponseEntity<Void> requestApproval(@RequestBody CardDto dto,
	                                                HttpSession session) {
	        String username = SessionUtil.getLoginUser(session).getUsername();
	        cardApprovalService.requestApproval(dto, username);
	        return ResponseEntity.ok().build();
	    }

	    // 전체 결재 요청 조회 (PENDING)
	    @GetMapping("/pending")
	    public List<CardApprovalRequestDTO> getPendingRequests() {
	        return cardApprovalService.getPendingRequestDtos();
	    }

	    // 결재 상세 보기
	    @GetMapping("/detail/{id}")
	    public ResponseEntity<CardApprovalRequestDTO> getRequestDetail(@PathVariable("id") Long id) {
	        CardApprovalRequestDTO dto = cardApprovalService.getRequestDetailDto(id);
	        return ResponseEntity.ok(dto);
	    }

	    // 승인 처리
	    @PostMapping("/approve/{id}")
	    public ResponseEntity<Void> approve(@PathVariable("id") Long id,
	                                        HttpSession session) {
	        String approver = SessionUtil.getLoginUser(session).getUsername();
	        cardApprovalService.approveRequest(id, approver);
	        return ResponseEntity.ok().build();
	    }

	    // 반려 처리
	    @PostMapping("/reject/{id}")
	    public ResponseEntity<Void> reject(@PathVariable("id") Long id,
	                                       @RequestParam("reason") String reason,
	                                       HttpSession session) {
	        String approver = SessionUtil.getLoginUser(session).getUsername();
	        cardApprovalService.rejectRequest(id, approver, reason);
	        return ResponseEntity.ok().build();
	    }
	}
