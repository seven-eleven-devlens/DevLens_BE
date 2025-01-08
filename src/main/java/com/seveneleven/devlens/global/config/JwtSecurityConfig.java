package com.seveneleven.devlens.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JWT 기반의 Security 설정을 적용하기 위한 클래스
 *
 * TokenProvider를 통해 생성된 JWT 토큰을 검증하는 필터를 Security Filter Chain에 추가합니다.
 */
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    /**
     * JWT 필터를 Security Filter Chain에 추가합니다.
     *
     * @param http HttpSecurity 객체로 보안 필터를 구성합니다.
     */
    @Override
    public void configure(HttpSecurity http) {
        JwtFilter jwtFilter = new JwtFilter(tokenProvider);
        // JWT 필터를 UsernamePasswordAuthenticationFilter 전에 추가
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}

