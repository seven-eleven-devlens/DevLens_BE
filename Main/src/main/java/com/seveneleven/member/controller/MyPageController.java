package com.seveneleven.member.controller;

import com.seveneleven.member.dto.MyPageGetMember;
import com.seveneleven.member.service.MyPageService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * 마이페이지 API Controller
 *
 * 핵심 기능 : 회원 상세 조회, 계정 정보 수정, 회원 탈퇴
 * */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    /**
     * 함수명 : memberDetail
     * 특정 회원의 정보를 조회합니다.
     *
     * @param loginId 조회할 회원의 로그인 ID (PathVariable).
     * @return HTTP 상태 코드 200 OK와 성공 응답 객체(APIResponse<MyPageGetMember>).
     */
    @GetMapping("/{loginId}")
    public ResponseEntity<APIResponse<MyPageGetMember>> memberDetail(@PathVariable String loginId) {

        MyPageGetMember response = myPageService.getMember(loginId);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, response));
    }

    // 계정 정보 수정
    @PostMapping("/{loginId}")
    public ResponseEntity<APIResponse<SuccessCode>> updateMember(@PathVariable String loginId) {


        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK));
    }


    // 회원 탈퇴



}
