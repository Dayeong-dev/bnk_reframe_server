package com.example.reframe.service;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.reframe.entity.User;
import com.example.reframe.repository.UserRepository;

@Service
public class AdminSignInService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public boolean validateUsernameAndPassword(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
        	return false;
        }

        boolean isAdminRole = user.getRole().equals("ROLE_ADMIN") || user.getRole().equals("ROLE_SUPERADMIN");

        return isAdminRole && passwordEncoder.matches(password, user.getPassword());
    }

    public boolean validateEmail(String username, String email) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
        	return false;
        }

        boolean isAdminRole = user.getRole().equals("ROLE_ADMIN") || user.getRole().equals("ROLE_SUPERADMIN");

        return isAdminRole && user.getEmail().equalsIgnoreCase(email);
    }
    
    // 인증 코드 발송
    public void sendAuthCode(String toEmail,
    						 String authCode) {
        // 이메일 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("BNK 관리자 로그인 인증번호");
        message.setText("BNK 관리자 로그인 인증번호:  < "+ authCode + " >");
        message.setFrom("codesender0001@gmail.com");

        javaMailSender.send(message);
    }

    public String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
	
}
