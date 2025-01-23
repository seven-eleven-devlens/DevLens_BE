package com.seveneleven.util.security.service;

import com.seveneleven.config.TokenProvider;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.security.dto.TokenResponse;
import com.seveneleven.util.security.repository.RefreshTokenRepositoryImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepositoryImpl refreshTokenRepository;

//    @Transactional
//    public void saveRefreshToken(String refreshToken, String userId) {
//        RefreshToken token = RefreshToken.builder()
//                .refreshToken(refreshToken)
//                .userId(userId)
//                .build();
//        refreshTokenRepository.save(token);
//    }
//
//    @Transactional
//    public void removeRefreshToken(String refreshToken) {
//        refreshTokenRepository.findRefreshTokenByRefreshToken(refreshToken)
//                .ifPresent(token -> refreshTokenRepository.delete(token));
//    }

    /**
     * 함수명 : refreshAccessToken
     * Refresh Token을 사용해 Access Token 재발급
     *
     * @param refreshToken 클라이언트로부터 전달받은 Refresh Token
     * @return 새로운 Access Token
     */
    @Transactional
    public TokenResponse refreshAccessToken(Authentication authentication, String refreshToken) {

        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 2. Refresh Token의 사용자 ID 확인
        String username = tokenProvider.getLoginId(refreshToken);


        // 3. Redis 또는 DB에서 Refresh Token 확인
        String storedRefreshToken = refreshTokenRepository.findByUsername(username);
        if (Objects.isNull(storedRefreshToken) || !storedRefreshToken.equals(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 4. 새로운 Access Token 생성 후 반환
        return tokenProvider.createTokens(authentication);
    }
}
