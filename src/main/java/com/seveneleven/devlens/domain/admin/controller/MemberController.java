package com.seveneleven.devlens.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class MemberController {

    // 회원 목록 조회
    @GetMapping("/member")
    public ResponseEntity<?> memberList() {

        return null;
    }

    // 회원 상세 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> memberDetail() {

        return null;
    }

    // 계정 생성
    @PostMapping("/member/")
    public ResponseEntity<?> createMember() {

        return null;
    }

    // 계정 수정
    @PutMapping("/member/{memberId}")
    public ResponseEntity<?> updateMember() {

        return null;
    }

    // 계정 삭제
    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<?> deleteMember() {

        return null;
    }

    // 회원 비밀번호 초기화
    @PutMapping("/member/{memberId}/reset-password")
    public ResponseEntity<?> resetPwd() {

        return null;
    }

}
