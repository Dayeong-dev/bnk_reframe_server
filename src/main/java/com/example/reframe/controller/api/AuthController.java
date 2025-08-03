package com.example.reframe.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.dto.UserDTO;
import com.example.reframe.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
	
	private final UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping("/signin")
	private ResponseEntity<UserDTO> signin(@RequestBody UserDTO userDTO) {		
		UserDTO user = userService.signin(userDTO);
		
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(userDTO);
	}
	
	@PostMapping("/signup")
	private ResponseEntity<Void> signup(@RequestBody UserDTO userDTO) {		

		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
