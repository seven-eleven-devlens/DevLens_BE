package com.seveneleven.devlens.global.config;

import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security 설정을 정의하는 클래스
 *
 * - JWT 기반 인증을 사용하도록 구성합니다.
 * - CORS, CSRF, 인증 및 권한 관리, 세션 정책 등을 설정합니다.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    /**
     * Spring Security 필터 체인을 구성합니다.
     *
     * @param http HttpSecurity 객체로 보안 설정을 정의합니다.
     * @return 구성된 SecurityFilterChain 객체
     * @throws Exception 보안 설정 중 예외가 발생할 경우
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .exceptionHandling(exceptionHandling -> // 인증 및 접근 거부 처리
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .headers(headers -> // H2-console 허용
                        headers.frameOptions(frameOptions -> frameOptions.sameOrigin())
                )
                .sessionManagement(sessionManagement -> // 세션 정책 설정 (STATELESS)
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(request -> request // URL별 접근 권한 설정
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers(
                                "/status",
                                "/images/**",
                                "/api/login",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        // JWT 필터를 추가
        JwtSecurityConfig jwtSecurityConfig = new JwtSecurityConfig(tokenProvider);
        jwtSecurityConfig.configure(http);

        return http.build();
    }

    /**
     * 비밀번호 암호화를 위한 PasswordEncoder를 Bean으로 등록합니다.
     *
     * @return BCryptPasswordEncoder 인스턴스
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CORS 설정을 정의합니다.
     *
     * - 모든 Origin, 메서드, 헤더를 허용합니다.
     * - 자격 증명 허용 설정을 활성화합니다.
     *
     * @return CORS 설정을 포함하는 CorsConfigurationSource 객체
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // 모든 Origin 허용
        configuration.addAllowedMethod("*");       // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*");       // 모든 헤더 허용
        configuration.setAllowCredentials(true);   // 자격 증명 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 적용
        return source;
    }

}

