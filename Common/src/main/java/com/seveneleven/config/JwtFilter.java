package com.seveneleven.config;

import com.seveneleven.entity.member.constant.TokenStatus;
import com.seveneleven.util.security.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenRepository tokenRepository;
    private final TokenProvider tokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            if(tokenProvider.validateToken(token)) {

                String loginId = tokenProvider.getLoginId(token); // null 이 들어감
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginId);

                if (userDetails != null && tokenRepository.existsByTokenAndStatus(token,TokenStatus.ACTIVE)) {
                    // UserDetsils, Password, Role -> 접근권한 인증 Token 생성
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    //현재 Request의 Security Context에 접근권한 설정
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response); // 다음 필터로 넘기기
    }
}

