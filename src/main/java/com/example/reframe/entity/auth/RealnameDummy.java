package com.example.reframe.entity.auth;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "realname_dummy")
@Data
public class RealnameDummy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    // 주민등록번호 앞자리(생년월일 6자리)
    @Column(name = "rrn_front", length = 6)
    private String rrnFront;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "carrier", length = 30)
    private String carrier;

    @Column(name = "verification_code", length = 10)
    private String verificationCode;

    @Column(name = "ci", length = 200)
    private String ci;
}
