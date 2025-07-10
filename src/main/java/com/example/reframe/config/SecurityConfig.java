package com.example.reframe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());
		
		http.formLogin(auth -> auth
				.loginPage("/signin/form")
				.loginProcessingUrl("/signin")
				.failureUrl("/signin/form?error")
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
		
		http.csrf(auth -> auth.disable());
		
		return http.build();
	}
}
