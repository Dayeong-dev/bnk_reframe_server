package com.example.reframe.controller.superadmin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.entity.User;
import com.example.reframe.repository.UserRepository;

@RestController
@RequestMapping("/superadmin/admin-manage")
public class AdminManageController {

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/show-admin-list")
	public List<User> showAdminList() {
		List<User> adminList = userRepository.findByRole("ROLE_ADMIN");
		return adminList;
		
	}
	
}
