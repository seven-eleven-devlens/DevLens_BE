package com.seveneleven.devlens.domain.admin.controller;

import com.seveneleven.devlens.domain.admin.dto.MemberDto;
import com.seveneleven.devlens.domain.admin.service.MemberMgmtService;
import com.seveneleven.devlens.domain.member.constant.MemberStatus;
import com.seveneleven.devlens.domain.member.constant.Role;
import com.seveneleven.devlens.global.config.Annotation.AdminAuthorize;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
//@AdminAuthorize
public class MemberMgmtController {

    private final MemberMgmtService memberMgmtService;

    /**
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
    @GetMapping("/member")
    public ResponseEntity<APIResponse<Page<MemberDto>>> getFilteredMembers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) MemberStatus status,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) String loginId,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<MemberDto> members = memberMgmtService.getFilteredMembers(name, status, role, loginId, pageable);
        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, members));
    }

    /**
     * 회원 상세 정보를 조회합니다.
     *
     * @param id 회원 ID (필수). 조회할 회원의 고유 식별자.
     * @return 회원 상세 정보를 포함한 응답 객체(APIResponse<MemberDto>).
     *         HTTP 상태 코드는 200 OK로 반환됩니다.
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<APIResponse<MemberDto>> memberDetail(@RequestParam String id) {
        // 회원 상세 정보 조회
        MemberDto memberDto = memberMgmtService.getMemberDetail(id);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, memberDto));
    }

    // 계정 생성
    @PostMapping("/member/")
    public ResponseEntity<APIResponse<SuccessCode>> createMember() {

        return null;
    }

    // 계정 수정
    @PutMapping("/member/{memberId}")
    public ResponseEntity<APIResponse<SuccessCode>>updateMember() {

        return null;
    }

    // 계정 삭제
    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<APIResponse<SuccessCode>> deleteMember() {

        return null;
    }

    // 회원 비밀번호 초기화
    @PutMapping("/member/{memberId}/reset-password")
    public ResponseEntity<APIResponse<SuccessCode>> resetPwd() {

        return null;
    }

}


