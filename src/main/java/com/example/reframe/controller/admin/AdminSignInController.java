package com.example.reframe.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.reframe.entity.User;
import com.example.reframe.repository.UserRepository;
import com.example.reframe.service.AdminSignInService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminSignInController {

	@Autowired
	private AdminSignInService adminSignInService;
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/login")
	public String login(@RequestParam("username") String username,
						@RequestParam("password") String password,
						@RequestParam(value = "email", required = false) String email,
						@RequestParam(value = "authCode", required = false) String authCode,
						HttpSession session,
						Model model) {
		
		// STEP 1: username, password 만 입력 시
		if (email == null || email.isEmpty()) {
			
			// username, password 올바른지 검증
			boolean valid = adminSignInService.validateUsernameAndPassword(username, password);
			
			// 검증 완료
			if (valid) {
				
				// 이메일 입력 요청
				model.addAttribute("username", username);
				model.addAttribute("password", password);
				model.addAttribute("step2", true);
				return "admin/signin-form"; // 로그인 폼 그대로 다시 보여줌
				
				// username, password 검증 실패
			} else {
				model.addAttribute("errorMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
				return "admin/signin-form";
			}
		}
		
		// STEP 2: email 입력 시
		if ((authCode == null || authCode.isEmpty()) && email != null) {
			// email 올바른지 검증
			boolean emailMatch = adminSignInService.validateEmail(username, email);
			// 검증 완료
			if (emailMatch) {
				// 인증 코드 생성
				String code = adminSignInService.generateCode();
				session.setAttribute("authCode", code);
				// 인증 코드 발송
				adminSignInService.sendAuthCode(email, code);
				
				model.addAttribute("step3", true);
				model.addAttribute("username", username);
				model.addAttribute("password", password);
				model.addAttribute("email", email);
				return "admin/signin-form";

			// email 검증 실패
			} else {
				model.addAttribute("step2", true);
				model.addAttribute("username", username);
				model.addAttribute("password", password);
				model.addAttribute("errorMsg", "이메일이 일치하지 않습니다.");
				return "admin/signin-form";
			}
		}

		// STEP 3: 인증 코드 확인
		if (authCode != null && !authCode.isEmpty()) {
			String savedCode = (String) session.getAttribute("authCode");
			if (authCode.equals(savedCode)) {

				// 인증 코드 삭제 
				session.removeAttribute("authCode"); 
				
				// 관리자의 등급 확인
				User user = userRepository.findByUsername(username);
			    
				// Security 연동
			    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
			            user.getUsername(), null, List.of(new SimpleGrantedAuthority(user.getRole())));

			    SecurityContextHolder.getContext().setAuthentication(auth);
			    
			    session.setAttribute(
		    	    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
		    	    SecurityContextHolder.getContext()
		    	);
			    
			    session.setAttribute("role", user.getRole());
			    session.setAttribute("loginInfo", user);
			    
				return "redirect:/admin/product-main"; // 로그인 완료
				
			} else {
				model.addAttribute("step3", true);
				model.addAttribute("username", username);
				model.addAttribute("password", password);
				model.addAttribute("email", email);
				model.addAttribute("errorMsg", "인증번호가 일치하지 않습니다.");
				return "admin/signin-form";
			}
		}
		
		// STEP 4: 관리자, 상위관리자 확인
		
		
		return "admin/signin-form";
	}
	
	// 인증번호 이메일 발송 처리 (AJAX)
    @PostMapping("/send-auth-code")
    public @ResponseBody ResponseEntity<String> sendAuthCode(@RequestParam("username") String username,
                                               @RequestParam("email") String email,
                                               HttpSession session) {

        boolean emailMatch = adminSignInService.validateEmail(username, email);
        if (!emailMatch) {
            return ResponseEntity.badRequest().body("이메일이 일치하지 않습니다.");
        }

        String code = adminSignInService.generateCode();
        session.setAttribute("authCode", code); // 세션에 인증코드 저장
        adminSignInService.sendAuthCode(email, code);

        return ResponseEntity.ok("인증번호를 이메일로 전송했습니다.");
    }
	
}
