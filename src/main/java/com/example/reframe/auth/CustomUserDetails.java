package com.example.reframe.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.reframe.entity.User;

public class CustomUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private final User user;
	
	public CustomUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		
		
		collect.add(new GrantedAuthority() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});

		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	public String getName() {
		return user.getName();
	}
	
	public String getPhone() {
		return user.getPhone();
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
	public String getUsertype() {
		return user.getUsertype();
	}
	
	public String getRole() {
		return user.getRole();
	}

}
