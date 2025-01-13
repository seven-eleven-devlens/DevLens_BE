package com.seveneleven.config;

import com.seveneleven.response.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * JWT 필터 클래스
 *
 * - HTTP 요청에서 JWT 토큰을 추출하고, 해당 토큰의 유효성을 검증합니다.
 * - 유효한 JWT 토큰이 있을 경우, 인증 정보를 Security Context에 저장합니다.
 */
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    /**
     * HTTP 요청에서 JWT 토큰을 추출하여 인증을 수행하고, Security Context에 저장합니다.
     *
     * @param servletRequest  클라이언트 요청 (ServletRequest)
     * @param servletResponse 서버 응답 (ServletResponse)
     * @param filterChain     필터 체인 (FilterChain)
     * @throws IOException      입출력 예외 발생 시
     * @throws ServletException 서블릿 처리 예외 발생 시
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        try {
            if (StringUtils.hasText(jwt)) {

                if (tokenProvider.validateToken(jwt)) {
                    Authentication authentication = tokenProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    httpServletResponse.sendError(ErrorCode.UNAUTHORIZED.getCode());
                    return;
                }
            } else {
                logger.info("JWT 토큰이 없습니다, URI: " + requestURI);
            }

            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            logger.info("필터 처리 중 예외 발생: " + e.getMessage());
            httpServletResponse.sendError(ErrorCode.JWT_FILTER_ERROR.getCode());
        }
    }

    /**
     * HTTP 요청 헤더에서 JWT 토큰을 추출합니다.
     *
     * @param request 클라이언트 요청 (HttpServletRequest)
     * @return 추출된 JWT 토큰. 없거나 형식이 올바르지 않은 경우 null 반환.
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}

