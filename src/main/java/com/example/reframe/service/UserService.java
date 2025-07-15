package com.example.reframe.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.reframe.dto.CorporateDTO;
import com.example.reframe.dto.CorporateUserDTO;
import com.example.reframe.dto.UserDTO;
import com.example.reframe.entity.CorporateUser;
import com.example.reframe.entity.User;
import com.example.reframe.repository.CorporateUserRepository;
import com.example.reframe.repository.UserRepository;
import com.example.reframe.util.CorpUserMapper;
import com.example.reframe.util.UserMapper;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	private final HttpSession session;
	private final UserRepository userRepository;
	private final CorporateUserRepository corporateUserRepository;
	private final PublicApiService publicApiService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final UserMapper userMapper = new UserMapper();
	private final CorpUserMapper corpUserMapper = new CorpUserMapper();
	
	public UserService(HttpSession session, 
					   UserRepository userRepository, 
					   CorporateUserRepository corporateUserRepository, 
					   PublicApiService publicApiService, 
					   BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.session = session;
		this.userRepository = userRepository;
		this.corporateUserRepository = corporateUserRepository;
		this.publicApiService = publicApiService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		
	}
	
	// 사업자 확인
	public boolean checkBusiness(CorporateUserDTO corpUserDTO) {
		// 사업자등록번호 포맷 (특수문자 제거)
		corpUserDTO.setBusinessNumber(toDigits(corpUserDTO.getBusinessNumber()));
		// 개업일자 포맷 (특수문자 제거)
		corpUserDTO.setBusinessStartDate(toDigits(corpUserDTO.getBusinessStartDate()));
		
		System.out.println(corpUserDTO);
		
		boolean isValidate = publicApiService.checkCorporateValidate(corpUserDTO);
		
		if(isValidate) {	// 유효한 사업자등록번호
			session.setAttribute("corpUserData", corpUserDTO);
			return true;
		}
		
		return false;
	}
	
	// 아이디 중복체크
	public boolean checkUsername(String username) {
		Optional<User> user = userRepository.findById(username);
		
		if(user.isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	// 개인 회원가입
	public UserDTO signup(UserDTO userDTO) {	
		if(userDTO == null || userDTO.getUsername() == null) {
			return null;
		}
		
		// 회원가입 시 공통 처리
		userDTO = prepareUserForRegistratio(userDTO, "ROLE_MEMBER", "P");
		
		// 회원 정보 등록
		User user = userRepository.save(userMapper.toEntity(userDTO));

		return userMapper.toDTO(user);
	}
	
	// 기업 회원가입
	@Transactional
	public CorporateUserDTO corpSignup(UserDTO userDTO) {
		if(userDTO == null || userDTO.getUsername() == null) {
			return null;
		}
		
		// 회원가입 시 공통 처리
		userDTO = prepareUserForRegistratio(userDTO, "ROLE_MEMBER", "C");
		
		// 회원 정보 등록
		User user = userRepository.save(userMapper.toEntity(userDTO));
		
		// 세션에 저장되어 있는 사업자 정보 조회
		CorporateUserDTO corporateUserDTO = (CorporateUserDTO)session.getAttribute("corpUserData");
		
		// 기업 회원 객체에 등록한 사용자 정보 저장
		corporateUserDTO.setUser(user);
		
		// 기업 회원 정보 등록
		CorporateUser corpUser =  corporateUserRepository.save(corpUserMapper.toEntity(corporateUserDTO));

		return corpUserMapper.toDTO(corpUser);
	}
	
	/** 회원가입 시 공통 처리(암호화, 포맷팅, 권한 및 유형 설정) */
	private UserDTO prepareUserForRegistratio(UserDTO userDTO, String role, String usertype) {
		// 비밀번호 암호화
		userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		
		// 전화번호 포맷 (특수문자 제거)
		userDTO.setPhone(toDigits(userDTO.getPhone()));
		
		// ROLE 지정
		userDTO.setRole(role);
		
		// 회원 타입 지정(P: 개인, C: 기업)
		userDTO.setUsertype(usertype);
		
		return userDTO;
	}
	
	/** 숫자 외 문자 제거 */
	private String toDigits(String str) {
		return str.replaceAll("[^\\d]", "");
	}
	
	/** 개인 회원 목록 불러오기*/
	public List<User> getPersonalUsers() {
		return userRepository.findByUsertype("P");
	}

	/** 기업 회원 목록 불러오기 */
	@Transactional
	public List<CorporateDTO> getCorporateUsers() {
		List<CorporateUser> corpList = corporateUserRepository.findAll();

		return corpList.stream().map(corp -> {
			CorporateDTO dto = new CorporateDTO();

			dto.setUsername(corp.getUsername());
			dto.setBusinessNumber(corp.getBusinessNumber());
			dto.setBusinessStartDate(corp.getBusinessStartDate());
			dto.setCeoName(corp.getCeoName());

			User user = corp.getUser();
			dto.setName(user.getName());
			dto.setEmail(user.getEmail());
			dto.setPhone(user.getPhone());

			return dto;
		}).collect(Collectors.toList());
	}

}
