package com.seveneleven.util.security;

import com.seveneleven.config.TokenProvider;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.dto.TokenResponse;
import com.seveneleven.util.security.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    /**
     * 함수명 : refreshAccessToken
     * Access Token 재발급 API
     *
     */
    @PostMapping("/refresh")
    public ResponseEntity<APIResponse<TokenResponse>> refreshAccessToken(@AuthenticationPrincipal Authentication authentication,
                                                                         @RequestHeader("X-Refresh-Token") String token) {

        System.out.println("authentication.getAuthorities() = " + authentication.getAuthorities());
        System.out.println("authentication.getName() = " + authentication.getName());
        System.out.println("token = " + token);

        TokenResponse tokens = refreshTokenService.refreshAccessToken(authentication, token);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, tokens));
    }
}
