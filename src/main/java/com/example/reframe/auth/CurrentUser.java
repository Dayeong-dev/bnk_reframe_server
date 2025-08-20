package com.example.reframe.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
	public Long id() {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth == null) return null;
		
		if (auth instanceof JwtAuthenticationToken jat) {
			
			Jwt jwt = jat.getToken();
			
			return Long.parseLong(jwt.getSubject());
		}
		
		return null;
	}
}