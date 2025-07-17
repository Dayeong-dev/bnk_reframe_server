package com.example.reframe.controller.superadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {

	@GetMapping("/approval-main")
	public String approvalMain() {
		return "/superadmin/approval-main";
	}
	
	@GetMapping("/admin-manage")
	public String management() {
		return "/superadmin/admin-manage";
	}
	
	@GetMapping("/card-approval-main")
	public String cardApprovalMain() {
		return "/superadmin/card-approval-main";
	}
	
}
