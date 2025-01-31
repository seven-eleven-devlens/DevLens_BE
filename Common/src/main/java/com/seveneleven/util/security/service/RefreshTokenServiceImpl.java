package com.seveneleven.util.security.service;

import com.seveneleven.config.CustomUserDetailsService;
import com.seveneleven.config.TokenProvider;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.security.dto.TokenResponse;
import com.seveneleven.util.security.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * 함수명 : refreshAccessToken
     * Refresh Token을 사용해 Access Token 재발급
     *
     * @param refreshToken 클라이언트로부터 전달받은 Refresh Token
     * @return 새로운 Access Token
     */
    @Transactional
    public TokenResponse refreshAccessToken(String accessToken, String refreshToken) {

        // 1. Access Token 검증 (Access Token이 있는지, 만료되었는지 check)
        if (tokenProvider.validateToken(accessToken)) {
            throw new BusinessException(ErrorCode.VALID_ACCESS_TOKEN);
        }

        // 2. Refresh Token 검증
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 3. Refresh Token의 사용자 ID 확인
        String memberId = tokenProvider.getMemberId(refreshToken);
        String loginId = tokenProvider.getLoginId(refreshToken);

        // 4. Redis 또는 DB에서 Refresh Token 확인
        String storedRefreshToken = refreshTokenRepository.findByUsername(memberId);
        if (Objects.isNull(storedRefreshToken) || !storedRefreshToken.equals(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginId);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // 5. 새로운 Access Token 생성 후 반환
        return tokenProvider.createTokens(authentication);
    }
}
