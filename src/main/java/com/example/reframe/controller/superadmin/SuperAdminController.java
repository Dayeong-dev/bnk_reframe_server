package com.example.reframe.controller.superadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {

	// 예/적금 결재
	@GetMapping("/approval-main")
	public String approvalMain() {
		return "/superadmin/approval-main";
	}
	
	// 관리자 관리
	@GetMapping("/admin-manage")
	public String management() {
		return "/superadmin/admin-manage";
	}
	
	// 카드 결재
	@GetMapping("/card-approval-main")
	public String cardApprovalMain() {
		return "/superadmin/card-approval-main";
	}
	
}
