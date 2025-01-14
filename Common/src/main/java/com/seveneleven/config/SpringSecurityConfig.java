package com.seveneleven.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정을 정의하는 클래스
 *
 * - JWT 기반 인증을 사용하도록 구성합니다.
 * - CORS, CSRF, 인증 및 권한 관리, 세션 정책 등을 설정합니다.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/member/**", "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
            "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/api/v1/auth/**"
    };

    /**
     * Spring Security 필터 체인을 구성합니다.
     *
     * @param http HttpSecurity 객체로 보안 설정을 정의합니다.
     * @return 구성된 SecurityFilterChain 객체
     * @throws Exception 보안 설정 중 예외가 발생할 경우
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> Customizer.withDefaults()) // CORS 설정
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화
            .formLogin((form) -> form.disable()) // FormLogin 비활성화

            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy( // 세션 정책 설정 (STATELESS)
                SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(exceptionHandling -> // 인증 및 접근 거부 처리
                    exceptionHandling
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                            .accessDeniedHandler(jwtAccessDeniedHandler)
            )
            .headers(headers -> // H2-console 허용
                    headers.frameOptions(frameOptions -> frameOptions.sameOrigin())
            )
            .addFilterBefore(new JwtFilter(customUserDetailsService, tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(AUTH_WHITELIST).permitAll()
                            .anyRequest().permitAll() // @PreAuthrization을 사용하기
            );

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

//    /**
//     * CORS 설정을 정의합니다.
//     *
//     * - 모든 Origin, 메서드, 헤더를 허용합니다.
//     * - 자격 증명 허용 설정을 활성화합니다.
//     *
//     * @return CORS 설정을 포함하는 CorsConfigurationSource 객체
//     */
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOriginPattern("*"); // 모든 Origin 허용
//        configuration.addAllowedMethod("*");       // 모든 HTTP 메서드 허용
//        configuration.addAllowedHeader("*");       // 모든 헤더 허용
//        configuration.setAllowCredentials(true);   // 자격 증명 허용
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 적용
//        return source;
//    }

}

