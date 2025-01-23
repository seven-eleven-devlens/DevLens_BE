package com.seveneleven.member.controller;

import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.dto.TokenResponse;
import com.seveneleven.util.security.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final RefreshTokenService refreshTokenService;

    /**
     * 함수명 : refreshAccessToken
     * Access Token 재발급 API
     *
     */
    @PostMapping("/refresh")
    public ResponseEntity<APIResponse<TokenResponse>> refreshAccessToken(@RequestHeader("X-Refresh-Token") String refreshToken) {

        System.out.println("/refresh ::::::::::::::::::: refreshToken = " + refreshToken);

        TokenResponse tokens = refreshTokenService.refreshAccessToken(refreshToken);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, tokens));
    }
}
