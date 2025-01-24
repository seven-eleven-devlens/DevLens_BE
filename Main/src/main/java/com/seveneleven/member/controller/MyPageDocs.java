package com.seveneleven.member.controller;



import com.seveneleven.member.dto.MyPageGetMember;
import com.seveneleven.member.dto.PatchMember;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/members")
@Tag(name = "My Page API", description = "APIs Related to My Page")
public interface MyPageDocs {
    @Operation(
            summary = "회원 상세 정보 조회",
            description = "특정 회원의 로그인 ID를 기반으로 상세 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 상세 정보 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MyPageGetMember.class)
                            )
                    )
            }
    )
    @GetMapping("")
    ResponseEntity<APIResponse<MyPageGetMember>> memberDetail(@AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(
            summary = "회원 정보 수정",
            description = "회원의 로그인 ID를 기반으로 회원 정보를 수정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 정보 수정 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PatchMember.Response.class)
                            )
                    )
            }
    )
    @PatchMapping("")
    ResponseEntity<APIResponse<PatchMember.Response>> updateMember(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                   @RequestBody PatchMember.Request requestDto);


    @Operation(
            summary = "회원 탈퇴",
            description = "회원의 로그인 ID를 기반으로 회원 상태를 변경하여 탈퇴 처리합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 탈퇴 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessCode.class)
                            )
                    )
            }
    )
    @DeleteMapping("")
    ResponseEntity<APIResponse<SuccessCode>> deleteMember(@AuthenticationPrincipal CustomUserDetails userDetails);


}
