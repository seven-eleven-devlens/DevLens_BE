package com.seveneleven.project.controller;

import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.PatchProjectCurrentStep;
import com.seveneleven.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Project API", description = "프로젝트 관련 API")
@RequestMapping("/api/projects")
public interface ProjectDocs {

    @GetMapping("/detail/{projectId}")
    @Operation(
            summary = "프로젝트 상세 조회",
            description = "특정 프로젝트의 상세 정보를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 프로젝트 상세 정보를 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetProjectDetail.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "projectId",
                            description = "조회할 프로젝트의 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<GetProjectDetail.Response>> getProjectDetail(
            @PathVariable Long projectId
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
