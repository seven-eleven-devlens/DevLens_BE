package com.seveneleven.controller;

import com.seveneleven.dto.LoginPost;
import com.seveneleven.dto.LoginResponse;
import com.seveneleven.dto.MemberDto;
import com.seveneleven.dto.MemberUpdate;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.Role;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.service.MemberMgmtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
* 관리자 회원 관리 API Controller
*
* 핵심 기능 : 회원 목록 조회, 회원 상세 조회, 회원 계정 생성(단일, 다중), 계정 수정, 계정 삭제, 비밀번호 초기화
* */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberMgmtController implements AdminMemberDocs {

    private final MemberMgmtService memberMgmtService;

    /**
     * 함수명 : login
     * 회원 로그인 처리 및 JWT 토큰 발급.
     *
     * @param request 로그인 요청 데이터를 담은 객체 (LoginRequest).
     * @return HTTP 상태 코드 200 OK와 JWT 토큰을 포함한 응답 헤더(APIResponse<SuccessCode>).
     *         Authorization 헤더에 Bearer 토큰 형식으로 JWT 토큰을 추가하여 반환합니다.
     */
    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponse>> login(@RequestBody LoginPost.Request request) {

        // 로그인 처리 및 JWT 토큰 발급
        LoginPost.Response response = memberMgmtService.login(request);

        // Access Token 쿠키 생성
        ResponseCookie accessTokenCookie = createCookie(
                "X-Access-Token",
                response.getAccessToken(),
                response.getExpiredAccess() / 1000
        );

        // Refresh Token 쿠키 생성
        ResponseCookie refreshTokenCookie = createCookie(
                "X-Refresh-Token",
                response.getRefreshToken(),
                response.getExpiredRefresh()/ 1000
        );

        // HTTP 응답에 쿠키 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        httpHeaders.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        // 성공 응답 반환
        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .headers(httpHeaders)
                .body(APIResponse.success(SuccessCode.OK, response.getCompanyInfo()));
    }

    /**
     * 함수명 : getFilteredMembers
     * 필터 조건에 따라 Member 목록을 조회합니다.
     *
     * @param name      필터링할 회원 이름 (옵션).
     * @param status    필터링할 회원 상태 (옵션).
     * @param role      필터링할 회원 역할 (옵션).
     * @param loginId   필터링할 로그인 ID (옵션).
     * @param pageable  페이지 번호, 크기, 정렬 정보를 포함하는 페이징 정보.
     *                  기본적으로 크기는 10, 정렬 기준은 "id", 정렬 방향은 오름차순(ASC)입니다.
     * @return 필터 조건에 해당하는 회원 목록 (MemberDto)을 페이지 형식으로 반환.
     *         ResponseEntity로 래핑하여 HTTP 응답으로 전달합니다.
     */
    @GetMapping("/admin/members")
    public ResponseEntity<APIResponse<Page<MemberDto.Response>>> getFilteredMembers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) MemberStatus status,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) String loginId,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<MemberDto.Response> members = memberMgmtService.getFilteredMembers(name, status, role, loginId, pageable);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, members));
    }

    /**
     * 함수명 : memberDetail
     * 회원 상세 정보를 조회합니다.
     *
     * @param memberId 회원 ID (필수). 조회할 회원의 고유 식별자.
     * @return 회원 상세 정보를 포함한 응답 객체(APIResponse<MemberDto>).
     *         HTTP 상태 코드는 200 OK로 반환됩니다.
     */
    @GetMapping("/admin/members/{memberId}")
    public ResponseEntity<APIResponse<MemberDto.Response>> memberDetail(@PathVariable Long memberId) {
        // 회원 상세 정보 조회
        MemberDto.Response memberDto = memberMgmtService.getMemberDetail(memberId);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, memberDto));
    }

    /**
     * 함수명 : createMember
     * 단일 회원 계정을 생성합니다.
     *
     * @param memberDto 생성할 회원의 정보를 담은 요청 객체 (MemberDto.Request)
     * @return 회원 상세 정보를 포함한 응답 객체(APIResponse<MemberDto.Response>).
     *         HTTP 상태 코드는 201 CREATED로 반환됩니다.
     */
    @PostMapping("/admin/members")
    public ResponseEntity<APIResponse<MemberDto.Response>> createMember(MemberDto.Request memberDto) {

        // 회원 생성 로직 호출
        MemberDto.Response createdMember = memberMgmtService.createMember(memberDto);

        return ResponseEntity.status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED, createdMember));
    }

    /**
     *  함수명 : createMembers
     *  다중 회원 계정을 생성합니다.
     *
     * @param memberDtos 생성할 회원의 정보를 담은 요청 객체 리스트 (MemberDto.Request)
     * @return 회원 상세 정보를 포함한 응답 객체(APIResponse<List<MemberDto.Response>).
     *         HTTP 상태 코드는 201 CREATED로 반환됩니다.
     */
    @PostMapping("/admin/members/batch")
    public ResponseEntity<APIResponse<List<MemberDto.Response>>> createMembers(@RequestBody List<MemberDto.Request> memberDtos) {
        List<MemberDto.Response> createdMembers = memberMgmtService.createMembers(memberDtos);
        return ResponseEntity.status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED, createdMembers));
    }


    /**
     * 함수명 : updateMember
     * 회원 정보를 수정합니다.
     *
     * @param loginId  수정할 회원의 고유 식별자 (필수).
     * @param memberDto 수정할 회원 정보를 담은 요청 객체 (MemberDto.PutRequest).
     * @return 수정된 회원 정보를 포함한 응답 객체 (APIResponse<MemberDto.Response>).
     *         HTTP 상태 코드는 200 OK로 반환됩니다.
     */
    @PatchMapping("/admin/members/{loginId}")
    public ResponseEntity<APIResponse<MemberDto.Response>> updateMember(@PathVariable String loginId,
                                                                        @RequestBody MemberUpdate.PatchRequest memberDto) {

        MemberDto.Response updatedMember = memberMgmtService.updateMember(loginId, memberDto);

        return ResponseEntity.status(SuccessCode.UPDATED.getStatus())
                .body(APIResponse.success(SuccessCode.UPDATED, updatedMember));
    }

    /**
     * 함수명 : deleteMember
     * 회원 계정 삭제합니다.
     *
     * @param loginId 수정할 회원의 고유 식별자 (필수).
     * @return 삭제된 회원에 대한 응답 객체 (APIResponse<SuccessCode>).
     *         HTTP 상태 코드는 200 DELETED로 반환됩니다.
     */
    @DeleteMapping("/admin/members/{loginId}")
    public ResponseEntity<APIResponse<SuccessCode>> deleteMember(@PathVariable String loginId) {

        memberMgmtService.deleteMember(loginId);

        return ResponseEntity.status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }

    /**
     * 함수명 : resetPwd
     * 회원 비밀번호를 초기화합니다.
     *
     * @param loginId 초기화할 회원의 고유 식별자 (PathVariable).
     * @return 초기화된 임시 비밀번호를 포함한 응답 객체 (APIResponse<String>).
     *         HTTP 상태 코드는 200 OK로 반환됩니다.
     */
    @PatchMapping("/admin/members/{loginId}/reset-password")
    public ResponseEntity<APIResponse<MemberUpdate.PatchResponse>> resetPwd(@PathVariable String loginId) {

        // 비밀번호 초기화
        MemberUpdate.PatchResponse response = memberMgmtService.resetPassword(loginId);

        // 응답으로 임시 비밀번호 반환
        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, response));
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
        return ResponseCookie.from(name, value)
                .httpOnly(false) // 클라이언트 측 스크립트에서 쿠키 접근 가능
                .secure(true) // HTTPS 환경에서만 전송
                .path("/") // 쿠키가 모든 경로에서 유효하도록 설정
                .maxAge(maxAge) // 쿠키 만료 시간 설정
                .sameSite("None")
                .build();
    }

}


