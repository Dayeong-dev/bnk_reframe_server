package com.example.reframe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.reframe.dto.CorporateUserDTO;
import com.example.reframe.dto.UserDTO;
import com.example.reframe.service.UserService;

@Controller
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/")
	public String root() {
		return "user/index";
	}
	
	@GetMapping("/signup")
	public String signUp() {
		return "user/signup-type";
	}
	
	@GetMapping("/signup/personal")
	public String signUpPersForm(Model model) {
		model.addAttribute("signupType", "personal");
		
		return "user/signup-form";
	}
	
	@GetMapping("/signup/corporate")
	public String signUpCorpForm(Model model) {
		model.addAttribute("signupType", "corporate");
		
		return "user/signup-form";
	}
	
	@GetMapping("/signup/corporate/verify")
	public String verify() {	// 사업자 확인 페이지
		return "user/corporate-verify";
	}
	
	@GetMapping("/username/check")
	public @ResponseBody ResponseEntity<String> checkUsername(@RequestParam("username") String username) {
		boolean result = userService.checkUsername(username);
		
		if(!result) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 아이디 입니다.");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("사용가능한 아이디 입니다.");
	}
	
	@GetMapping("/signin/form")
	public String signinForm(@RequestParam(value="needLogin", required=false) String needLogin, 
							 @RequestParam(value="error", required=false) String error, 
							 Model model) {
		
		if(needLogin != null) {
			model.addAttribute("msg", "로그인이 필요합니다.");
		}
		if(error != null) {
			model.addAttribute("msg", "아이디 또는 비밀번호가 올바르지 않습니다.");
		}
		
		return "user/signin-form";
	}
	
	@PostMapping("/business/check")
	public String checkBusiness(CorporateUserDTO corpUser, RedirectAttributes rttr) {
		System.out.println(corpUser);
		
		boolean result = userService.checkBusiness(corpUser);
		
		if(!result) {	// 확인 실패
			rttr.addFlashAttribute("msg", "사업자 확인에 실패하였습니다. ");
			return "redirect:/signup/corporate/verify";
		}
		
		return "redirect:/signup/corporate";
	}
	
	@PostMapping("/signup/personal")
	public String signupPersonal(UserDTO userDTO, RedirectAttributes rttr) {
		UserDTO user = userService.signup(userDTO);
		
		if(user == null) {
			rttr.addFlashAttribute("msg", "회원가입 중 문제가 발생했습니다.");
			return "redirect:/signup/personal";
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/signup/corporate")
	public String signupCorporate(UserDTO userDTO, RedirectAttributes rttr) {
		CorporateUserDTO corpUser = userService.corpSignup(userDTO);
		
		if(corpUser == null) {
			rttr.addFlashAttribute("msg", "회원가입 중 문제가 발생했습니다.");
			return "redirect:/signup/personal";
		}
		
		return "redirect:/";
	}
}
