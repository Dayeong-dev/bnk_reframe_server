package com.example.reframe.service.auth;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.reframe.dto.CorporateDTO;
import com.example.reframe.dto.CorporateUserDTO;
import com.example.reframe.dto.auth.RefreshIssueResult;
import com.example.reframe.dto.auth.TokenResponse;
import com.example.reframe.dto.auth.UserDTO;
import com.example.reframe.entity.auth.CorporateUser;
import com.example.reframe.entity.auth.User;
import com.example.reframe.repository.auth.CorporateUserRepository;
import com.example.reframe.repository.auth.UserRepository;
import com.example.reframe.service.PublicApiService;
import com.example.reframe.service.account.AccountService;
import com.example.reframe.util.CorpUserMapper;
import com.example.reframe.util.JWTUtil;
import com.example.reframe.util.UserMapper;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	private final HttpSession session;
	private final UserRepository userRepository;
	private final CorporateUserRepository corporateUserRepository;
	private final PublicApiService publicApiService;
	private final RefreshTokenService refreshTokenService;
	private final AccountService accountService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JWTUtil jwtUtil;
	
	private final UserMapper userMapper = new UserMapper();
	private final CorpUserMapper corpUserMapper = new CorpUserMapper();
	
	public UserService(HttpSession session, 
					   UserRepository userRepository, 
					   CorporateUserRepository corporateUserRepository,
					   PublicApiService publicApiService, 
					   RefreshTokenService refreshTokenService, 
					   AccountService accountService, 
					   BCryptPasswordEncoder bCryptPasswordEncoder, 
					   JWTUtil jwtUtil) {
		this.session = session;
		this.userRepository = userRepository;
		this.corporateUserRepository = corporateUserRepository;
		this.publicApiService = publicApiService;
		this.refreshTokenService = refreshTokenService;
		this.accountService = accountService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtUtil = jwtUtil;
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
		Optional<User> user = userRepository.findByUsername(username);
		
		if(user.isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	// 개인 회원가입
	@Transactional
	public UserDTO signup(UserDTO userDTO) {	
		try {
			if(userDTO == null || userDTO.getUsername() == null) {
				return null;
			}
			
			// 회원가입 시 공통 처리
			userDTO = prepareUserForRegistratio(userDTO, "ROLE_MEMBER", "P");
			
			// 회원 정보 등록
			User user = userRepository.save(userMapper.toEntity(userDTO));
			
			// 더미 계좌 발급
			accountService.createDefaultAccount(user);

			return userMapper.toDTO(user);
		} catch(Exception e) {
			throw new RuntimeException("회원가입 중 오류 발생");
		}
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
	public UserDTO prepareUserForRegistratio(UserDTO userDTO, String role, String usertype) {
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

			dto.setUserId(corp.getUserId());
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

	public void updateRoles(List<UserDTO> roleList) {
	    for (UserDTO dto : roleList) {
	        User user = userRepository.findByUsername(dto.getUsername())
	        		.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

	        user.setRole(dto.getRole());
	        userRepository.save(user);
	    }
	}
	
	public TokenResponse signin(UserDTO userDTO) {
		User user = userRepository.findByUsername(userDTO.getUsername())
				.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
		
		if(user == null || !bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
			throw new RuntimeException("아이디 또는 비밀번호가 잘못되었습니다. ");
		}
		
		UserDTO currUserDTO = userMapper.toDTO(user);
		RefreshIssueResult issueResult = refreshTokenService.issue(currUserDTO);
		
		String accessToken = jwtUtil.createAccessToken(currUserDTO, RefreshTokenService.ACCESS_EXPIRATION_MS);
		String refreshToken = jwtUtil.createRefreshToken(issueResult.getJti(), currUserDTO, RefreshTokenService.REFRESH_EXPIRATION_MS);
		
		TokenResponse tokenResponse = new TokenResponse("Bearer", accessToken, refreshToken);
		
		return tokenResponse;
	}
}
