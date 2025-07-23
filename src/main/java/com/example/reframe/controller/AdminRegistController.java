package com.example.reframe.controller;

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
import com.example.reframe.util.UserMapper;

@RestController
@RequestMapping("/superadmin/admin-manage")
public class AdminRegistController {

	private final UserMapper userMapper = new UserMapper();
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody UserDTO userDTO) {
        try {
        	if(userDTO == null || userDTO.getUsername() == null) {
    			return null;
    		}
        	// 회원가입 시 공통 처리
        	userDTO = userService.prepareUserForRegistratio(userDTO, "ROLE_ADMIN", "P");
        	
        	// 회원 정보 등록
    		userRepository.save(userMapper.toEntity(userDTO));
        	
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다.");
        }
    }
}
