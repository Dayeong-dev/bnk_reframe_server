package com.example.reframe.repository.fcm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reframe.entity.fcm.FcmTemplate;

public interface FcmTemplateRepository extends JpaRepository<FcmTemplate,Long>{
	List<FcmTemplate> findByActiveTrue();
	List<FcmTemplate> findByActiveTrueOrderByUpdatedAtDesc();
}