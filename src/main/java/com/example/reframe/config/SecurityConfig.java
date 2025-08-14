package com.example.reframe.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import com.example.reframe.auth.CustomAuthenticationEntryPoint;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
    JwtDecoder jwtDecoder(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.issuer:}") String issuer
    ) {
    	SecretKey key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        OAuth2TokenValidator<Jwt> timestamp = new JwtTimestampValidator(java.time.Duration.ZERO); // 테스트는 0초
        if (issuer != null && !issuer.isBlank()) {
            OAuth2TokenValidator<Jwt> withIssuer = new JwtIssuerValidator(issuer);
            decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withIssuer, timestamp));
        } else {
            decoder.setJwtValidator(timestamp);
        }
        return decoder;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthConverter() {
        JwtGrantedAuthoritiesConverter gac = new JwtGrantedAuthoritiesConverter();
        gac.setAuthoritiesClaimName("roles");
        gac.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter conv = new JwtAuthenticationConverter();
        conv.setJwtGrantedAuthoritiesConverter(gac);

        return conv;
    }

    @Bean
    org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        var cfg = new org.springframework.web.cors.CorsConfiguration();
        cfg.setAllowedOriginPatterns(List.of("*"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type"));
        cfg.setExposedHeaders(List.of("Authorization"));
        cfg.setAllowCredentials(false);
        var src = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }

    @Bean
    @Order(0)
    SecurityFilterChain apiChain(HttpSecurity http,
                                 JwtDecoder jwtDecoder,
                                 JwtAuthenticationConverter jwtAuthConverter) throws Exception {
        http.securityMatcher("/mobile/**");

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/mobile/auth/**").permitAll()
            .anyRequest().authenticated()
        );

        http.exceptionHandling(ex -> ex
            .authenticationEntryPoint((req, res, e) -> {	// 401
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.setContentType("application/json;charset=UTF-8");
                res.getWriter().write("{\"error\":\"unauthorized\"}");
            })
            .accessDeniedHandler((req, res, e) -> {			// 403
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.setContentType("application/json;charset=UTF-8");
                res.getWriter().write("{\"error\":\"forbidden\"}");
            })
        );

        http.oauth2ResourceServer(oauth -> oauth
            .jwt(jwt -> jwt
                .decoder(jwtDecoder)
                .jwtAuthenticationConverter(jwtAuthConverter)
            )
        );

        return http.build();
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
