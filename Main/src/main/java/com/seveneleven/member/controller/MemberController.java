package com.seveneleven.member.controller;

import com.seveneleven.config.JwtFilter;
import com.seveneleven.config.TokenProvider;
import com.seveneleven.entity.member.Member;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.dto.MemberJoinDto;
import com.seveneleven.member.dto.MemberPatch;
import com.seveneleven.member.service.MemberService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.response.SuccessCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
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

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService memberService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<APIResponse<SuccessCode>> login(MemberJoinDto dto) {

        Member member = memberService.getUserWithAuthorities(dto.getUserid()).get();

        if (!ObjectUtils.isEmpty(member)) {

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getUserid(), dto.getPw());

            // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
            String jwt = tokenProvider.createToken(authentication);

            HttpHeaders httpHeaders = new HttpHeaders();
            // response header에 jwt token에 넣어줌
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);


            // tokenDto를 이용해 response body에도 넣어서 리턴
            // return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);


            return ResponseEntity.status(SuccessCode.OK.getStatus())
                    .headers(httpHeaders) // 헤더 추가
                    .build(); // APIResponse 반환

        }
        throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<APIResponse<SuccessCode>> join(@RequestBody MemberJoinDto dto) {
        try {
            // memberService.join(dto.getUserid(), dto.getPw());
            return ResponseEntity.status(SuccessCode.OK.getStatus())
                    .body(APIResponse.success(SuccessCode.OK));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.DUPLICATE_USER_ID);
        }
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