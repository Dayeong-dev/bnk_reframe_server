package com.example.reframe.entity.fcm;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fcm_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FcmTemplate {
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String groupCode;

	    private String title;
	    private String body;

	    // cron 표현식 (예: "0 0 9 * * ?" → 매일 오전 9시)
	    private String cron;

	    // 활성화 여부 (중지된 템플릿 무시)
	    private boolean active;
	    
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
}
