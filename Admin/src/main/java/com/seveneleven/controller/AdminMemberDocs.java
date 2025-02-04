package com.seveneleven.controller;


import com.seveneleven.dto.GetMemberList;
import com.seveneleven.dto.MemberDto;
import com.seveneleven.dto.MemberUpdate;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.Role;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
@Tag(name = "Administrator Member Management API", description = "관리자 회원 관리 관련 API")
public interface AdminMemberDocs {
    @Operation(
            summary = "회원 목록 조회",
            description = "필터 조건에 따라 회원 목록을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 목록 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/members")
    ResponseEntity<APIResponse<GetMemberList.Response>> getFilteredMembers( GetMemberList.Request memberList );


    @Operation(
            summary = "회원 상세 조회",
            description = "회원 ID로 회원 상세 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberDto.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(name = "memberId", description = "조회할 회원의 ID", required = true, example = "1")
            }
    )
    @GetMapping("/members/{loginId}")
    ResponseEntity<APIResponse<MemberDto.Response>> memberDetail(@PathVariable Long memberId) ;

    @Operation(
            summary = "회원 생성",
            description = "단일 회원 계정을 생성합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "회원 생성 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberDto.Response.class)
                            )
                    )
            }
    )
    @PostMapping("/members")
    ResponseEntity<APIResponse<MemberDto.Response>> createMember(@RequestBody MemberDto.Request memberDto);

    @Operation(
            summary = "다중 회원 생성",
            description = "여러 회원 계정을 한 번에 생성합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "다중 회원 생성 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = List.class)
                            )
                    )
            }
    )
    @PostMapping("/members/batch")
    ResponseEntity<APIResponse<List<MemberDto.Response>>> createMembers(@RequestBody List<MemberDto.Request> memberDtos);

    @Operation(
            summary = "회원 수정",
            description = "회원 정보를 수정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 수정 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberDto.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(name = "memberId", description = "수정할 회원의 ID", required = true, example = "1")
            }
    )
    @PatchMapping("/members/{memberId}")
    ResponseEntity<APIResponse<MemberDto.Response>> updateMember(@PathVariable Long memberId,
                                                                 @RequestBody MemberUpdate.PatchRequest memberDto);


    @Operation(
            summary = "회원 삭제",
            description = "회원 계정을 삭제합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 삭제 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessCode.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(name = "memberId", description = "삭제할 회원의 ID", required = true, example = "1")
            }
    )
    @DeleteMapping("/members/{loginId}")
    ResponseEntity<APIResponse<SuccessCode>> deleteMember(@PathVariable Long memberId);

    @Operation(
            summary = "비밀번호 초기화",
            description = "회원 비밀번호를 초기화합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "비밀번호 초기화 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberUpdate.PatchResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(name = "memberId", description = "비밀번호를 초기화할 회원의 ID", required = true, example = "1")
            }
    )
    @PatchMapping("/members/{memberId}/reset-password")
    ResponseEntity<APIResponse<MemberUpdate.PatchResponse>> resetPwd(HttpServletRequest request, @PathVariable Long memberId);
}
