package com.example.reframe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.reframe.auth.CustomAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
		this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Order(1)
	SecurityFilterChain adminFilterChane(HttpSecurity http) throws Exception {
		http.securityMatcher("/admin/**", "/superadmin/**", "/admin/signin-form", "/admin/login", "/admin/send-auth-code");
		
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/admin/signin-form", "/admin/login", "/admin/send-auth-code").permitAll()
				.requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")
				.requestMatchers("/superadmin/**").hasRole("SUPERADMIN")
				.anyRequest().denyAll());
		
		http.formLogin(auth -> auth.disable());
		
		http.logout(auth -> auth
			    .logoutUrl("/admin/signout")
			    .logoutSuccessUrl("/admin/signin-form")
			    .invalidateHttpSession(true)
			    .deleteCookies("JSESSIONID")
			    .permitAll());
		
		http.exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint));
		
		http.csrf(auth -> auth.disable());
		
		return http.build();
	}
	
	@Bean
	@Order(2)
	SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {		
		http.securityMatcher("/**");
		
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/qna/**").hasRole("MEMBER")
				.anyRequest().permitAll());
		
		http.formLogin(auth -> auth
				.loginPage("/signin/form")
				.loginProcessingUrl("/signin")
				.defaultSuccessUrl("/")
				.failureUrl("/signin/form?error")
				.permitAll())
			.exceptionHandling(exception -> exception
			    .authenticationEntryPoint((request, response, authException) -> {
			        response.sendRedirect("/signin/form?needLogin");
			    }));
		
		http.logout(auth -> auth
				.logoutUrl("/signout")
			    .logoutSuccessHandler((request, response, authentication) -> {
			        String referer = request.getHeader("Referer");
			        
			        if (referer == null || referer.contains("/logout")) {
			            response.sendRedirect("/");
			        } else {
			            response.sendRedirect(referer);
			        }
			    })
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll());
		
		http.exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint));
		
		http.csrf(auth -> auth.disable());
		
		return http.build();
	}
}
