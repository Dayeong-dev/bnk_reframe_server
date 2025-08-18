package com.example.reframe.util;

public class AccountNumberGenerator {
	// 계좌번호 생성(예시: 112-1234-5678-10)
    public String newNumber() {
        // 은행 코드(112)
        String bankCode = "112";
        
        // 4자리
        String first = String.format("%04d", (int)(Math.random() * 10000));

        // 4자리
        String middle = String.format("%04d", (int)(Math.random() * 10000));
        
        // 2자리
        String last = String.format("%02d", (int)(Math.random() * 100));

        // 최종 조합
        return bankCode + first + middle + last; // "1121234567810"
    }
}
