package com.example.reframe.repository.fcm;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.fcm.FcmToken;

public interface FcmTokenRepository extends JpaRepository<FcmToken,Long>{
	
	 List<FcmToken> findAll();
	 
	 Optional<FcmToken> findByToken(String token);
	 
	 List<FcmToken> findByUserId(String userId);

	 List<FcmToken> findByGroupCode(String groupCode);

}

