package com.seveneleven.project.controller;

import com.seveneleven.project.dto.GetMemberAuthorization;
import com.seveneleven.project.dto.GetProjectAuthorization;
import com.seveneleven.project.dto.PostProjectAuthorization;
import com.seveneleven.response.APIResponse;
import com.seveneleven.util.security.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/projects")
public interface ProjectAuthorizationDocs {

    @PutMapping("/{projectId}/authorizations/members")
    @Operation(
            summary = "프로젝트 권한 편집",
            description = "특정 단계의 프로젝트 권한을 편집합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "프로젝트 권한이 성공적으로 생성되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostProjectAuthorization.Response.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "207",
                            description = "일부 요청이 실패하였습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostProjectAuthorization.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<PostProjectAuthorization.Response>> postProjectAuthorization(
            @PathVariable Long projectId,
            @RequestBody PostProjectAuthorization.Request requestDto
    );

    @GetMapping("/{projectId}/authorizations")
    @Operation(
            summary = "프로젝트 권한 조회",
            description = "특정 단계의 프로젝트 권한을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "프로젝트 권한이 성공적으로 조회되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProjectAuthorization.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<GetProjectAuthorization.Response>> getProjectAuthorization(
            @PathVariable Long projectId
    );

    @GetMapping("/{projectId}/authorizations/members")
    @Operation(
            summary = "로그인한 회원의 접근 권한 조회",
            description = "로그인한 회원의 접근 권한을 확인합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원의 접근 권한이 성공적으로 조회되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetMemberAuthorization.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<GetMemberAuthorization.Response>> getMemberAuthorization(
            @PathVariable Long projectId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );
}
