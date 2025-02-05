package com.seveneleven.member.controller;

import com.seveneleven.config.TokenProvider;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.dto.TokenResponse;
import com.seveneleven.util.security.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AdminAuthController implements AdminAuthDocs {
    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;
    @Value("${spring.profiles.active}")
    private String mod;

    /**
     * 함수명 : refreshAccessToken
     * Access Token 재발급 API
     *
     */
    @PostMapping("/refresh")
    public ResponseEntity<APIResponse<TokenResponse>> refreshAccessToken (@CookieValue("X-Access-Token") String accessToken,
                                                                          @CookieValue("X-Refresh-Token") String refreshToken) {

        TokenResponse tokens = refreshTokenService.refreshAccessToken(accessToken, refreshToken);


        // Access Token 쿠키 생성
        ResponseCookie accessTokenCookie = createCookie(
                "X-Access-Token",
                tokens.accessToken(),
                tokenProvider.getAccessTokenExpireTime() / 1000
        );

        // Refresh Token 쿠키 생성
        ResponseCookie refreshTokenCookie = createCookie(
                "X-Refresh-Token",
                tokens.refreshToken(),
                tokenProvider.getRefreshTokenExpireTime()/ 1000
        );

        log.info("[Admin] 액세스 토큰 재발급 : "+accessTokenCookie.toString());
        log.info("[Admin] 리프레시 토큰 재발급 : "+refreshTokenCookie.toString());

        // HTTP 응답에 쿠키 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        httpHeaders.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());


        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, tokens));
    }

    /**
     * 쿠키 생성 메서드.
     *
     * @param name     쿠키 이름
     * @param value    쿠키 값
     * @param maxAge   쿠키 만료 시간 (초 단위)
     * @return 생성된 ResponseCookie
     */
    private ResponseCookie createCookie(String name, String value, Long maxAge) {

        log.info("[Admin] "+mod+" 환경에서 토큰 재발급 중 ...");

        ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from(name, value)
                .secure(true)
                .httpOnly(true)
                .sameSite("None")
                .maxAge(maxAge)
                .path("/");


        // 배포 환경에서만 도메인 적용
        if ("prod".equals(mod)) {
            cookieBuilder.domain(".devlens.work");
        }

        return cookieBuilder.build();
    }
}
