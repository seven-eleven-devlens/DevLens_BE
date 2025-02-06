package com.seveneleven.project.controller;

import com.seveneleven.project.dto.GetCompanyProject;
import com.seveneleven.project.dto.GetProjectList;
import com.seveneleven.project.dto.PatchProjectCurrentStep;
import com.seveneleven.response.APIResponse;
import com.seveneleven.util.security.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Tag(name = "Project Dashboard API", description = "프로젝트 대시보드 관련 API")
public interface MyProjectDocs {

    @GetMapping("/projects")
    @Operation(
            summary = "프로젝트 목록 조회",
            description = "사용자의 프로젝트 목록과 현재 회사의 프로젝트 목록을 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 프로젝트 목록을 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProjectList.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<GetProjectList.Response>> getMyProject(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("step") String step
            );

    @GetMapping("/companies/{companyId}/projects")
    @Operation(
            summary = "회사 프로젝트 목록 조회",
            description = "지정된 회사의 프로젝트 목록을 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 회사 프로젝트 목록을 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetCompanyProject.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<GetCompanyProject.Response>> getMyCompanyProject(
            @PathVariable Long companyId
    );


    @PatchMapping("/projects/{projectId}/current-steps/{stepId}")
    @Operation(
            summary = "프로젝트 현재 단계 수정",
            description = "특정 프로젝트의 현재 단계를 지정한 단계로 수정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "프로젝트 현재 단계가 성공적으로 수정되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PatchProjectCurrentStep.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<PatchProjectCurrentStep.Response>> patchProjectCurrentStep(
            @PathVariable Long projectId,
            @PathVariable Long stepId
    );
}
