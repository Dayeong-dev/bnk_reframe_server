package com.example.reframe.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.service.UserService;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{type}") // type: P or C
    public List<?> getUserList(@PathVariable("type") String type) {
        if ("P".equalsIgnoreCase(type)) {
            return userService.getPersonalUsers(); // 개인회원 리스트
        } else if ("C".equalsIgnoreCase(type)) {
            return userService.getCorporateUsers(); // 기업회원 리스트
        } else {
            throw new IllegalArgumentException("잘못된 회원 타입입니다.");
        }
    }
	
}
