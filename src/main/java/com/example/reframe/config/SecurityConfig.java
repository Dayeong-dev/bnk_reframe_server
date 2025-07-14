package com.example.reframe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(auth -> auth
//				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/qna/**").hasRole("MEMBER")
				.anyRequest().permitAll());
		
		http.formLogin(auth -> auth
				.loginPage("/signin/form")
				.loginProcessingUrl("/signin")
				.defaultSuccessUrl("/")
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
