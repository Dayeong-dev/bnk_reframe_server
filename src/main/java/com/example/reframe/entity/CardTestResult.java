package com.example.reframe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TEST_RESULT")
@Getter
@NoArgsConstructor
public class CardTestResult {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    @Column(name = "RESULT_ID")
    private Long resultId;//결과ID, 자동증가

    @Column(name = "RESULT_TYPE", nullable = false)
    private String resultType;// 결과 유형

    @Column(name = "COUNT", nullable = false)
    private Long count = 0L;  // 결과 유형 카운트

    public CardTestResult(String resultType) {
        this.resultType = resultType;
        this.count = 1L; // 최초 생성 시 1부터 시작
    }

    public void incrementCount() {
        this.count++;
    }
}