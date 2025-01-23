package com.seveneleven.util.security.service;

import com.seveneleven.util.security.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface RefreshTokenService {
//    void saveRefreshToken(String refreshToken, String userId);
//    void removeRefreshToken(String refreshToken);
 TokenResponse refreshAccessToken(Authentication authentication, String refreshToken);
}
