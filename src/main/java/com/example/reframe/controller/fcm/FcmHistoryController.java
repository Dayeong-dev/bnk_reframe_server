package com.example.reframe.controller.fcm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reframe.entity.fcm.FcmHistory;
import com.example.reframe.repository.fcm.FcmHistoryRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/fcm/fcm-history")
public class FcmHistoryController {

	@Autowired
	private FcmHistoryRepository fcmHistoryRepository;
	
	// 발송 이력 최신순
	@GetMapping
	public List<FcmHistory> getAllHistory() {
		return fcmHistoryRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
	}
}