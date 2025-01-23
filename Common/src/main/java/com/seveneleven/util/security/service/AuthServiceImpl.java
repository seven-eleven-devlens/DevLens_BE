package com.seveneleven.util.security.service;

import com.seveneleven.config.TokenProvider;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.security.repository.RefreshTokenRepositoryImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepositoryImpl refreshTokenRepository;
//    private final RefreshTokenService refreshTokenService;

    @Transactional
    // Refresh Token 만료 여부 확인
    public boolean isRefreshTokenExpired(String refreshToken) {
        try {
            LocalDateTime expiredDate = tokenProvider.getExpirationFromToken(refreshToken);
            return expiredDate.isBefore(LocalDateTime.now());
        } catch (Exception e) {
            return true;
        }
    }

    // Refresh Token 유효성 검증
    public boolean isValidRefreshToken(String refreshToken) {
        String n = refreshTokenRepository.findRefreshTokenByRefreshToken(refreshToken);

        return n==null?false:true;
    }

}
