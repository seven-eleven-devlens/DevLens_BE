package com.seveneleven.config;

import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 필터 클래스
 *
 * - HTTP 요청에서 JWT 토큰을 추출하고, 해당 토큰의 유효성을 검증합니다.
 * - 유효한 JWT 토큰이 있을 경우, 인증 정보를 Security Context에 저장합니다.
 */
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
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
        if ("/auth/refresh".equals(requestURI)) {
            filterChain.doFilter(request, response); // Refresh 요청은 그대로 통과
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        String refreshToken = request.getHeader(REFRESH_HEADER);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7);

            // Access Token이 유효할 때
            if(tokenProvider.validateToken(accessToken)) {
                setAuthentication(accessToken);
            }
            // Access Token이 만료되었지만, Refresh Token이 유효할 때
            else if(refreshToken != null && tokenProvider.validateToken(refreshToken)) {
                // throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN);
                 handleBusinessException(response, new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN));
                 return;
            }
            // Access Token과 Refresh Token 모두 만료되었을 때
            else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Access Token 과 Refresh Token이 만료되었습니다.");
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

