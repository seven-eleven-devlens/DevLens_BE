package com.seveneleven.config;

import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 인증된 사용자가 필요한 권한 없이 보호된 리소스에 접근하려고 할 때 처리하는 클래스
 *
 * - Spring Security에서 권한 부족으로 인한 접근 거부 시 403 (Forbidden) 에러를 반환합니다.
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 권한이 없는 사용자가 요청을 보낼 때 호출됩니다.
     *
     * @param request               클라이언트 요청 (HttpServletRequest)
     * @param response              서버 응답 (HttpServletResponse)
     * @param accessDeniedException 발생한 접근 거부 예외 (AccessDeniedException)
     * @throws IOException 입출력 예외 발생 시
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        // 403 Forbidden 상태 코드 반환
        handleBusinessException(response, new BusinessException(ErrorCode.FORBIDDEN));
    }

    // BusinessException 응답 처리 메서드
    private void handleBusinessException(HttpServletResponse response, BusinessException ex) throws IOException {
        response.setStatus(ex.getErrorCode().getStatus().value()); // HTTP 상태 코드 설정
        response.setContentType("application/json");
        response.getWriter().write("{\"code\": \"" + ex.getErrorCode().getCode() + "\", \"message\": \"" +  ex.getMessage() + "\", \"data\": \"\" }");
    }
}

