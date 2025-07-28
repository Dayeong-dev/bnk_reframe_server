package com.example.reframe.util;

import com.example.reframe.entity.User;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {
	
	// 로그인된 관리자 정보 가져오기
    public static User getLoginUser(HttpSession session) {
        User loginInfo = (User)session.getAttribute("loginInfo");
        return loginInfo;
    }

    // 권한 가져오기 
    public static String getRole(HttpSession session) {
        User user = getLoginUser(session);
        return (user != null) ? user.getRole() : null;
    }
}
