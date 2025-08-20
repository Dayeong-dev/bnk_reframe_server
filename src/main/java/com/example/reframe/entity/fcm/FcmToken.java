package com.example.reframe.entity.fcm;

import jakarta.persistence.Column;
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
@Table(name="fcm_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FcmToken {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;		

    private String userId;
    
    @Column(unique = true)
    private String token;
    
    @Column(name = "group_code")
    private String groupCode;
    
    // 생성자
    public FcmToken(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }


}
