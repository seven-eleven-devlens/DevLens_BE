package com.seveneleven.controller;

import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.dto.TokenResponse;
import com.seveneleven.util.security.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AdminAuthController implements AdminAuthDocs {
    private final RefreshTokenService refreshTokenService;

    /**
     * 함수명 : refreshAccessToken
     * Access Token 재발급 API
     *
     */
    @PostMapping("/refresh")
    public ResponseEntity<APIResponse<TokenResponse>> refreshAccessToken(@CookieValue("X-Access-Token") String accessToken,
                                                                         @CookieValue("X-Refresh-Token") String refreshToken) {

        TokenResponse tokens = refreshTokenService.refreshAccessToken(accessToken, refreshToken);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, tokens));
    }
}
