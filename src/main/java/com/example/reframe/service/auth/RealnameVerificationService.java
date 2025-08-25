package com.example.reframe.service.auth;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reframe.auth.CurrentUser;
import com.example.reframe.dto.auth.RealnameVerificationDTO;
import com.example.reframe.entity.auth.RealnameDummy;
import com.example.reframe.entity.auth.RealnameVerification;
import com.example.reframe.entity.auth.User;
import com.example.reframe.repository.auth.RealnameDummyRepository;
import com.example.reframe.repository.auth.RealnameVerificationRepository;
import com.example.reframe.util.RealnameVerificationMapper;

@Service
public class RealnameVerificationService {

	private final RealnameVerificationRepository verificationRepository;
	private final RealnameDummyRepository realnameDummyRespository;
	private final CurrentUser currentUser;
	
	private final RealnameVerificationMapper rvMapper = new RealnameVerificationMapper();
	
	public RealnameVerificationService(RealnameVerificationRepository verificationRepository,
									   RealnameDummyRepository realnameDummyRespository, 
									   CurrentUser currentUser) {
		this.verificationRepository = verificationRepository;
		this.realnameDummyRespository = realnameDummyRespository;
		this.currentUser = currentUser;
	}
	
	@Transactional(readOnly = true)
	public RealnameVerificationDTO checkVerification() {
		Long uid = currentUser.id();
        if (uid == null) throw new IllegalStateException("로그인이 필요합니다.");
        
        Optional<RealnameVerification> latestOpt = verificationRepository.findTopByUser_IdOrderByVerifiedAtDesc(uid);
        
        if(latestOpt.isEmpty()) {
        	throw new IllegalStateException("실명인증 이력이 없습니다.");
        }
        
        RealnameVerification rv = latestOpt.get();
        LocalDateTime now = LocalDateTime.now();
        
        if(rv.getVerifiedAt() == null || rv.getExpiresAt() == null) {
        	throw new IllegalStateException("실명인증 이력이 올바르지 않습니다. 재인증이 필요합니다.");
        }

        // 실명인증 유효
        if(!rv.getVerifiedAt().isAfter(now) && rv.getExpiresAt().isAfter(now)) {
        	return rvMapper.toDTO(rv);
        }
        
        throw new IllegalStateException("실명인증이 만료되었습니다. 재인증이 필요합니다.");

	}
	
	@Transactional
    public String requestCode(RealnameVerificationDTO rvDTO) {
		
		RealnameDummy rd = realnameDummyRespository.findByNameAndRrnFrontAndCarrierAndPhoneNumber(
        		rvDTO.getName(), rvDTO.getRrnFront(), rvDTO.getCarrier(), rvDTO.getPhone()
        ).orElseThrow(() -> new IllegalStateException("일치하는 사용자 정보가 없습니다."));

        // 랜덤 6자리 코드 생성
        String code = String.format("%06d", new SecureRandom().nextInt(1_000_000));
        
        rd.setVerificationCode(code);
        realnameDummyRespository.save(rd);

        return code;
    }


    @Transactional
    public VerifyStatus verifyCode(RealnameVerificationDTO rvDTO, String inputCode) {
    	
    	RealnameDummy rd = realnameDummyRespository.findByNameAndRrnFrontAndCarrierAndPhoneNumber(
        		rvDTO.getName(), rvDTO.getRrnFront(), rvDTO.getCarrier(), rvDTO.getPhone()
        ).orElseThrow(() -> new IllegalStateException("일치하는 사용자 정보가 없습니다."));

        if (rd.getVerificationCode() == null) {
            return VerifyStatus.NO_CODE;
        }

        if (!rd.getVerificationCode().equals(inputCode)) {
            return VerifyStatus.MISMATCH;
        }

        Long uid = currentUser.id();
        
        if (uid == null) throw new IllegalStateException("로그인이 필요합니다.");
        
        User user = new User();
        user.setId(uid);
        
        RealnameVerification rv = new RealnameVerification();
        
        rv.setUser(user);
        rv.setName(rd.getName());
        rv.setPhone(rd.getPhoneNumber());
        rv.setCarrier(rd.getCarrier());
        rv.setRrnFront(rd.getRrnFront());
        rv.setCi(rd.getCi());
        rv.setVerifiedAt(LocalDateTime.now());
        rv.setExpiresAt(LocalDateTime.now().plusYears(1));
        

        verificationRepository.save(rv);

        return VerifyStatus.VERIFIED;
    }
}
