package com.seveneleven.member.controller;

import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.member.dto.MyPageGetMember;
import com.seveneleven.member.dto.PatchMember;
import com.seveneleven.member.service.MemberFileService;
import com.seveneleven.member.service.MyPageService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.file.dto.FileMetadataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/*
 * 마이페이지 API Controller
 *
 * 핵심 기능 : 회원 상세 조회, 계정 정보 수정, 회원 탈퇴
 * */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MyPageController implements MyPageDocs{

    private final MyPageService myPageService;
    private final MemberFileService memberFileService;

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
     * 회원 탈퇴 메서드로 상태를 변경합니다.
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

    /**
     * 함수명 : uploadProfileImage
     * 계정 프로필 이미지를 업로드합니다.
     *
     * @param memberId 해당 회원 Id
     * @return ResponseEntity<APIResponse<SuccessCode>> 성공 응답 객체
     */
    @PostMapping(value="/{memberId}/profile-image", consumes = "multipart/form-data")
    public ResponseEntity<APIResponse<SuccessCode>> uploadProfileImage(@PathVariable("memberId") Long memberId,
                                                                       @RequestParam("file") MultipartFile file) {
        //TODO) userDetails에서 회원 정보 가져오기
        Long uploaderId = 1L;

        memberFileService.uploadProfileImage(file, memberId, uploaderId);

        return ResponseEntity
                .status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED));
    }

    /**
     * 함수명 : getProfileImage
     * 계정 프로필 이미지를 조회합니다.
     *
     * @param memberId 해당 회원 Id
     * @return ResponseEntity<APIResponse<SuccessCode>> 성공 응답 객체
     */
    @GetMapping(value = "/{memberId}/profile-image")
    public ResponseEntity<APIResponse<FileMetadataDto>> getProfileImage(@PathVariable("memberId") Long memberId) {
        //memberId로 프로필 이미지 조회
        FileMetadataDto profileImage = memberFileService.getProfileImage(memberId);

        //반환
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, profileImage));
    }

    /**
     * 함수명 : deleteProfileImage
     * 계정 프로필 이미지를 삭제합니다.
     * @param memberId 해당 회원 Id
     * @param userDetails 수행자 정보
     * @return ResponseEntity<APIResponse<SuccessCode>> 성공 응답 객체
     */
    @DeleteMapping(value="/{memberId}/profile-image")
    public ResponseEntity<APIResponse<SuccessCode>> deleteProfileImage(@PathVariable("memberId") Long memberId) {
        //TODO) UserDetails 확인
        Long uploaderId = 1L;

        memberFileService.deleteProfileImage(memberId, uploaderId);

        return ResponseEntity
                .status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }





}
