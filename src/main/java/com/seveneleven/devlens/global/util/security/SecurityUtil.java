package com.seveneleven.devlens.global.util.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;

/**
 * Security Context에서 인증 정보를 관리하고, 현재 사용자 정보를 제공하는 유틸리티 클래스
 */
public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    /**
     * Security Context에서 현재 사용자 이름(username)을 가져옵니다.
     *
     * @return Optional에 감싼 사용자 이름(username).
     *         인증 정보가 없거나 username을 확인할 수 없는 경우 Optional.empty()를 반환합니다.
     */
    public static Optional<String> getCurrentUsername() {
        // Security Context에서 Authentication 객체를 가져옴
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없는 경우 로그 출력 후 빈 Optional 반환
        if (authentication == null) {
            logger.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;

        // Authentication 객체에서 username 추출
        if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(username);
    }
}
