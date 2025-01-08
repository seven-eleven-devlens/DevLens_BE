package com.seveneleven.devlens.global.config;

import com.seveneleven.devlens.global.response.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 인증되지 않은 사용자가 보호된 리소스에 접근하려고 할 때 처리하는 클래스
 *
 * - Spring Security에서 인증 실패 시 401 (Unauthorized) 에러를 반환합니다.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 인증되지 않은 사용자가 요청을 보낼 때 호출됩니다.
     *
     * @param request       클라이언트 요청 (HttpServletRequest)
     * @param response      서버 응답 (HttpServletResponse)
     * @param authException 인증 예외 (AuthenticationException)
     * @throws IOException 입출력 예외 발생 시
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 401 Unauthorized 상태 코드 반환
        response.sendError(ErrorCode.UNAUTHORIZED.getCode());
    }
}

