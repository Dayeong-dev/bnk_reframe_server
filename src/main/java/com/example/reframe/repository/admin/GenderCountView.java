package com.example.reframe.repository.admin;

import com.example.reframe.enums.Gender;

public interface GenderCountView {
	// 사용자 성별(M/F/null)
    Gender getGender();
    // 해당 성별의 사용자 수(중복 없는 사용자 수)
    long getCount();
}
