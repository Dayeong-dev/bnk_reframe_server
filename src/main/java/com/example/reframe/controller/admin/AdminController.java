package com.example.reframe.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/product-main")
	public String root () {
		return "admin/product-main";
	}
	@GetMapping("/sidebar")
	public String sidebar () {
		return "admin/fragments/sidebar";
	}
	@GetMapping("/header")
	public String header () {
		return "admin/fragments/header";
	}
	
	@GetMapping("/layout")
	public String layout () {
		return "admin/fragments/layout";
	}
	
	@GetMapping("/card-main")
	public String cardMain(){
		return "admin/card-main";
	}
	
	
}
