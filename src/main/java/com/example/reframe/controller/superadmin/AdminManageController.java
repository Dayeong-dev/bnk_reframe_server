package com.example.reframe.controller.superadmin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.UserDTO;
import com.example.reframe.entity.User;
import com.example.reframe.repository.UserRepository;
import com.example.reframe.service.UserService;

@RestController
@RequestMapping("/superadmin/admin-manage")
public class AdminManageController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/show-admin-list")
	public List<User> showAdminList() {
		List<User> adminList = userRepository.findByRole("ROLE_ADMIN");
		return adminList;
		
	}
	@PostMapping("/update-role")
	public ResponseEntity<?> updateRoles(@RequestBody List<UserDTO> roleList) {
	    try {
	        userService.updateRoles(roleList);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body("변경 실패: " + e.getMessage());
	    }
	}
}
