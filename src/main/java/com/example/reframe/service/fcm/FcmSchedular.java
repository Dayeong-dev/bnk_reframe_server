package com.example.reframe.service.fcm;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.reframe.entity.fcm.FcmTemplate;
import com.example.reframe.repository.fcm.FcmTemplateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FcmSchedular {

	private final FcmTemplateRepository templateRepo;
    private final FcmService fcmService;

    // 매 1분마다 실행하여 활성화된 템플릿 조회
    @Scheduled(cron = "0 0 9 * * * ")	
    public void sendScheduledPush() {
        List<FcmTemplate> activeTemplates = templateRepo.findByActiveTrue();

        for (FcmTemplate template : activeTemplates) {
            System.out.println("🕒 자동 푸시 발송 시작: " + template.getTitle());

            try {
            	if(template.getGroupCode().equals("all")) {
            		fcmService.sendToAll(template.getTitle(), template.getBody());
            	}else {
            		System.out.println("그룹 확인....." + template.getGroupCode());
            		fcmService.sendToGroup(template.getGroupCode(), template.getTitle(), template.getBody());
            	}
            } catch (Exception e) {
                System.err.println("❌ 푸시 발송 실패 (템플릿 ID: " + template.getId() + ")");
                e.printStackTrace();
            }
        }
    }
}