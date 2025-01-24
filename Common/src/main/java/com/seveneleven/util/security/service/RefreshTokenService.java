package com.seveneleven.util.security.service;

import com.seveneleven.util.security.dto.TokenResponse;
public interface RefreshTokenService {
    TokenResponse refreshAccessToken(String accessToken, String refreshToken);
}
