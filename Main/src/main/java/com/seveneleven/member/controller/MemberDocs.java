package com.seveneleven.member.controller;

import com.seveneleven.member.dto.CheckMailPostRequest;
import com.seveneleven.member.dto.LoginPost;
import com.seveneleven.member.dto.LoginResponse;
import com.seveneleven.member.dto.MemberPatch;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/main/api")
@Tag(name = "Login Page API", description = "APIs Related to Login Page")
public interface MemberDocs {
    @Operation(
            summary = "회원 로그인",
            description = "회원 로그인 처리 및 JWT 토큰 발급. 발급된 Access Token과 Refresh Token은 쿠키로 반환됩니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "로그인 실패")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "회원 로그인 요청 데이터",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginPost.Request.class)
                    )
            )
    )
    @PostMapping("/login")
    ResponseEntity<APIResponse<LoginResponse>> login(@RequestBody LoginPost.Request request);

    @Operation(
            summary = "회원 로그아웃",
            description = "클라이언트에서 전달된 Access Token을 사용해 로그아웃을 처리합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그아웃 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/logout")
    ResponseEntity<APIResponse<SuccessCode>> logout(@CookieValue("X-Access-Token") String accessToken);

    @Operation(
            summary = "이메일 인증 메일 전송",
            description = "로그인한 사용자에게 이메일 인증 메일을 전송합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "이메일 전송 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/send-mail")
    ResponseEntity<APIResponse<String>> sendMail(@AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(
            summary = "이메일 인증 확인",
            description = "사용자가 입력한 인증 키를 검증하여 인증 성공 여부를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "인증 키 확인 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "이메일 인증 키 확인 요청 데이터",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CheckMailPostRequest.class)
                    )
            )
    )
    @PostMapping("/check-mail")
    ResponseEntity<APIResponse<Boolean>> checkMail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CheckMailPostRequest request
    );

    @Operation(
            summary = "비밀번호 재설정",
            description = "회원 비밀번호를 초기화하여 임시 비밀번호를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "비밀번호 재설정 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "비밀번호 재설정 요청 데이터",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = MemberPatch.Request.class)
                    )
            )
    )
    @PatchMapping("/members/reset-password")
    ResponseEntity<APIResponse<MemberPatch.Response>> resetPwd(
            HttpServletRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody MemberPatch.Request requestDto
    );

}
