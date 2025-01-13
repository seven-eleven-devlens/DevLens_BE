package com.seveneleven.member.controller;

import com.seveneleven.member.dto.MyPageGetMember;
import com.seveneleven.member.dto.PatchMember;
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

    /**
     * 함수명 : updateMember
     * 회원의 정보를 수정합니다.
     *
     * @param loginId 수정할 회원의 로그인 ID (PathVariable).
     * @param requestDto 수정할 정보를 담은 DTO (RequestBody).
     * @return HTTP 상태 코드 200 OK와 성공 응답 객체(APIResponse<PatchMember.Response>).
     */
    @PatchMapping("/{loginId}")
    public ResponseEntity<APIResponse<PatchMember.Response>> updateMember(@PathVariable String loginId,
                                                                     @RequestBody PatchMember.Request requestDto) {

        PatchMember.Response response = myPageService.updateMember(loginId, requestDto);

        return ResponseEntity.status(SuccessCode.UPDATED.getStatus())
                .body(APIResponse.success(SuccessCode.UPDATED, response));
    }


    /**
     * 함수명 : deleteMember
     * 회원 탈퇴(삭제) 메서드로 상태를 변경합니다.
     *
     * @param loginId 수정할 회원의 로그인 ID (PathVariable).
     * @return HTTP 상태 코드 200 OK와 성공 응답 객체(APIResponse<SuccessCode>).
     */
    @DeleteMapping("/{loginId}")
    public ResponseEntity<APIResponse<SuccessCode>> deleteMember(@PathVariable String loginId) {

        myPageService.deleteMember(loginId);

        return ResponseEntity.status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }


}
