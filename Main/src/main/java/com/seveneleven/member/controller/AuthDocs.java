package com.seveneleven.member.controller;

import com.seveneleven.response.APIResponse;
import com.seveneleven.util.security.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "Access Token & Refresh Token 관련 API")
public interface AuthDocs {

    @Operation(
            summary = "Access Token 재발급",
            description = "Access Token이 만료된 경우 Refresh Token을 통해 새로운 Access Token을 재발급합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Access Token 재발급 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패 또는 토큰 유효성 검증 실패"
                    )
            },
            parameters = {
                    @Parameter(
                            name = "X-Access-Token",
                            description = "기존 Access Token",
                            in = ParameterIn.COOKIE,
                            required = true
                    ),
                    @Parameter(
                            name = "X-Refresh-Token",
                            description = "Refresh Token",
                            in = ParameterIn.COOKIE,
                            required = true
                    )
            }
    )
    @PostMapping("/refresh")
    ResponseEntity<APIResponse<TokenResponse>> refreshAccessToken(
            @CookieValue("X-Access-Token") String accessToken,
            @CookieValue("X-Refresh-Token") String refreshToken
    );
}
