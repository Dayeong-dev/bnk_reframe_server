package com.example.reframe.session;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.reframe.entity.User;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalSessionAdvice {

    @ModelAttribute("loginInfo")
    public User loginInfo(HttpSession session) {
        return (User) session.getAttribute("loginInfo");
    }

    @ModelAttribute("role")
    public String role(HttpSession session) {
        return (String) session.getAttribute("role");
    }
}
