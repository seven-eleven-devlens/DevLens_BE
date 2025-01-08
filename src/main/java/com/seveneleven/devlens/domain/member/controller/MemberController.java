package com.seveneleven.devlens.domain.member.controller;

import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.member.dto.MemberJoinDto;
import com.seveneleven.devlens.domain.member.dto.TokenDto;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.member.service.MemberService;
import com.seveneleven.devlens.global.config.Annotation.AdminAuthorize;
import com.seveneleven.devlens.global.config.Annotation.UserAuthorize;
import com.seveneleven.devlens.global.config.JwtFilter;
import com.seveneleven.devlens.global.config.TokenProvider;
import com.seveneleven.devlens.global.exception.BusinessException;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.ErrorCode;
import com.seveneleven.devlens.global.response.SuccessCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/*
* security, jwt 테스트 확인을 위해 임시로 만든 controller
*
* */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MemberController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService memberService;


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

    @PostMapping("/join")
    public ResponseEntity<APIResponse<SuccessCode>> join(@RequestBody MemberJoinDto dto) {
        try {
            // memberService.join(dto.getUserid(), dto.getPw());
            return ResponseEntity.status(SuccessCode.OK.getStatus())
                    .body(APIResponse.success(SuccessCode.OK));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.DUPLICATE_USER_ID);
        }
    }


    // 테스트용 입니다. (삭제 예정)

//    @GetMapping("/setting/admin")
//    @AdminAuthorize
//    public String adminSettingPage() {
//        return "admin_setting";
//    }
//
//    @GetMapping("/setting/user")
//    @UserAuthorize
//    public String userSettingPage() {
//        return "user_setting";
//    }
}