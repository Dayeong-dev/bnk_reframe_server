package com.example.reframe.testdata;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.entity.auth.User;
import com.example.reframe.repository.auth.CorporateUserRepository;
import com.example.reframe.repository.auth.UserRepository;

@SpringBootTest
public class TestUserFactory {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CorporateUserRepository corpUserRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//@Test
	/* 개인 회원 더미 데이터 추가 */
	public void setUserDummies() {
		IntStream.rangeClosed(1, 3).forEach(i -> {
			User user = User.builder()
					.username("user" + i)
					.password(bCryptPasswordEncoder.encode("user1234!"))
					.name("user" + i)
					.phone("0101111" + (i * 1111))
					.email("user" + i + "@green.com")
					.usertype("P")
					.role("ROLE_MEMBER")
					.build();
			
			userRepository.save(user);
		});
	}
	
	@Test
	/* 관리자 회원 더미 데이터 추가 */
	public void setAdminDummy () {
		User user = User.builder()
				.username("12345678")
				.password(bCryptPasswordEncoder.encode("1234"))
				.name("관리자1")
				.phone("01011112222")
				.email("admin1@naver.com")
				.usertype("P")
				.role("ROLE_ADMIN")
				.build();
		
		userRepository.save(user);
	}
	
	@Test
	/* 상위 관리자 회원 더미 데이터 추가 */
	public void setSuperAdminDummy() {
		User user = User.builder()
				.username("23456789")
				.password(bCryptPasswordEncoder.encode("1234"))
				.name("관리자2")
				.phone("01011113333")
				.email("admin2@naver.com")
				.usertype("P")
				.role("ROLE_SUPERADMIN")
				.build();
		
		userRepository.save(user);
	}
}
