package com.seveneleven.config;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {
    /**
     * 함수명 : loadUserByUsername
     * 사용자 이름을 기반으로 사용자 세부 정보를 로드합니다.
     *
     * @param loginId 사용자의 로그인Id
     * @return 사용자 세부 정보
     */
    UserDetails loadUserByUsername(String loginId);
}
