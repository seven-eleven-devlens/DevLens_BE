package com.seveneleven.config;

import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.security.dto.Token;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * JWT 필터 클래스
 *
 * - HTTP 요청에서 JWT 토큰을 추출하고, 해당 토큰의 유효성을 검증합니다.
 * - 유효한 JWT 토큰이 있을 경우, 인증 정보를 Security Context에 저장합니다.
 */
public class JwtFilter extends OncePerRequestFilter {

    public static final String ACCESS_HEADER  = "X-Access-Token";
    public static final String REFRESH_HEADER = "X-Refresh-Token";
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenProvider tokenProvider;

    public JwtFilter(CustomUserDetailsService customUserDetailsService, TokenProvider tokenProvider) {
        this.customUserDetailsService = customUserDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI(); // 요청 경로 확인

        // Refresh 요청은 필터를 통과시키도록 설정
        if ("/api/auth/refresh".equals(requestURI)) {
            filterChain.doFilter(request, response); // Refresh 요청은 그대로 통과
            return;
        }

        // 쿠키에서 토큰 가져오기
        Token token = new Token();

        if (Objects.nonNull(request.getCookies())) {
            for (Cookie cookie : request.getCookies()) {
                if (ACCESS_HEADER.equals(cookie.getName())) {
                    token.setAccessToken(cookie.getValue());
                } else if (REFRESH_HEADER.equals(cookie.getName())) {
                    token.setRefreshToken(cookie.getValue());
                }
            }
        }


        if(Objects.nonNull(token) && Objects.nonNull(token.getAccessToken())) {

            // Access Token이 유효할 때
            if(tokenProvider.validateToken(token.getAccessToken())) {
                setAuthentication(token.getAccessToken());
            }
            // Access Token이 만료되었지만, Refresh Token이 유효할 때
            else if(token.getRefreshToken() != null && tokenProvider.validateToken(token.getRefreshToken())) {
                handleBusinessException(response, new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN));
                return;
            }
            // Access Token과 Refresh Token 모두 만료되었을 때
            else {
                handleBusinessException(response, new BusinessException(ErrorCode.UNAUTHORIZED));
                return;
            }
        }

        filterChain.doFilter(request, response); // 다음 필터로 넘기기
    }

    /**
     * Access Token으로 사용자 인증 설정
     *
     * @param token 유효한 Access Token
     */
    private void setAuthentication(String token) {
        String loginId = tokenProvider.getLoginId(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginId);

        if (userDetails != null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    // BusinessException 응답 처리 메서드
    private void handleBusinessException(HttpServletResponse response, BusinessException ex) throws IOException {
        response.setStatus(ex.getErrorCode().getStatus().value()); // HTTP 상태 코드 설정
        response.setContentType("application/json");
        response.getWriter().write("{\"code\": \"" + ex.getErrorCode().getCode() + "\", \"message\": \"" +  ex.getMessage() + "\", \"data\": \"\" }");
    }

}

