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
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/projects")
public interface ProjectAuthorizationDocs {

    @PostMapping("/steps/{stepId}/authorizations")
    @Operation(
            summary = "프로젝트 권한 생성",
            description = "특정 단계의 프로젝트 권한을 생성합니다.",
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
            @PathVariable Long stepId,
            @RequestBody PostProjectAuthorization.Request requestDto
    );

    @GetMapping("/steps/{stepId}/authorizations")
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
            @PathVariable Long stepId
    );

    @GetMapping("/{projectId}/authorizations/members")
    @Operation(
            summary = "멤버 권한 조회",
            description = "특정 프로젝트의 멤버 권한을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "멤버 권한이 성공적으로 조회되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetMemberAuthorization.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<GetMemberAuthorization.Response>> getMemberAuthorization(
            @PathVariable Long projectId,
            CustomUserDetails customUserDetails
    );
}
