package com.seveneleven.member.controller;

import com.seveneleven.config.JwtFilter;
import com.seveneleven.member.dto.LoginRequest;
import com.seveneleven.member.dto.MemberPatch;
import com.seveneleven.member.service.MemberService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/*
 * 로그인 페이지 API Controller
 *
 * 핵심 기능 : 로그인, 로그아웃, 이메일 인증, 비밀번호 재설정
 * */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 함수명 : login
     * 회원 로그인 처리 및 JWT 토큰 발급.
     *
     * @param request 로그인 요청 데이터를 담은 객체 (LoginRequest).
     * @return HTTP 상태 코드 200 OK와 JWT 토큰을 포함한 응답 헤더(APIResponse<SuccessCode>).
     *         Authorization 헤더에 Bearer 토큰 형식으로 JWT 토큰을 추가하여 반환합니다.
     */
    @PostMapping("/login")
    public ResponseEntity<APIResponse<SuccessCode>> login(@RequestBody LoginRequest request) {

        // 로그인 처리 및 JWT 토큰 발급
        String token = memberService.login(request);

        // JWT 토큰을 Authorization 헤더에 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);

        // 성공 응답 반환
        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .headers(httpHeaders)
                .body(APIResponse.success(SuccessCode.OK));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<APIResponse<SuccessCode>> logout(@RequestHeader("Authorization") String token) {

         memberService.logout(token);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK));
    }

    // 이메일 인증
    @PostMapping("/auth/email")
    public ResponseEntity<APIResponse<SuccessCode>> emailAuth(){
        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK));
    }


    /**
     * 함수명 : resetPwd
     * 회원 비밀번호를 재설정합니다.
     *
     * @param request 재설정할 회원 정보 Dto(RequestBody).
     * @return 초기화된 임시 비밀번호를 포함한 응답 객체 (APIResponse<MemberPatch.Response>).
     *         HTTP 상태 코드는 200 OK로 반환됩니다.
     */
    @PatchMapping("/members/{loginId}/reset-password")
    public ResponseEntity<APIResponse<MemberPatch.Response>> resetPwd(@RequestBody MemberPatch.Request request) {

        // 비밀번호 초기화
        MemberPatch.Response response = memberService.resetPassword(request);

        // 응답으로 임시 비밀번호 반환
        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, response));
    }
}