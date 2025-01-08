package com.seveneleven.devlens.domain.admin.controller;

import com.seveneleven.devlens.domain.admin.dto.MemberDto;
import com.seveneleven.devlens.domain.admin.service.MemberMgmtService;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
// @PreAuthorize("hasRole('ADMIN')")  // 관리자 권한만 접근 가능하도록 설정
public class MemberMgmtController {

    private final MemberMgmtService memberMgmtService;

    // 회원 목록 조회
    @GetMapping("/member")
    public ResponseEntity<APIResponse<Object>> memberList() {

        List<MemberDto> memberDtoList = memberMgmtService.getAllMembers();
        System.out.println(memberDtoList.toString());

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, "회원 목록을 성공적으로 조회했습니다.", memberDtoList));
    }

    // 회원 상세 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<APIResponse<SuccessCode>> memberDetail() {

        return null;
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


