package com.seveneleven.project.controller;

import com.seveneleven.project.dto.GetCompanyProject;
import com.seveneleven.project.dto.GetProjectList;
import com.seveneleven.project.dto.PatchProjectCurrentStep;
import com.seveneleven.project.service.ProjectService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyProjectController implements MyProjectDocs {

    private final ProjectService projectService;

    /**
     * 함수명 : getMyProject()
     * 현재 진행중인 내 프로젝트와 우리 회사의 프로젝트를 반환하는 함수
     */
    @GetMapping("/projects")
    public ResponseEntity<APIResponse<GetProjectList.Response>> getMyProject(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("status") String projectStatusCode
    ) {
        return ResponseEntity
                .status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(
                        SuccessCode.OK,
                        projectService.getMyProjectList(
                                userDetails.getMember().getId(),
                                projectStatusCode
                        )));
    }

    /**
     * 함수명 : getCompanyProject()
     * 자신의 회사의 프로젝트를 반환하는 함수
     */
    @Override
    @GetMapping("/companies/{companyId}/projects")
    public ResponseEntity<APIResponse<GetCompanyProject.Response>> getMyCompanyProject(
            @PathVariable Long companyId
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(SuccessCode.OK, projectService.getCompanyProject(companyId)));
    }

    /**
     * 함수명 : patchProjectCurrentStep()
     * 프로젝트의 현재 단계를 수정하는 함수
     */
    @Override
    @PatchMapping("/projects/{projectId}/current-steps/{stepId}")
    public ResponseEntity<APIResponse<PatchProjectCurrentStep.Response>> patchProjectCurrentStep(
            @PathVariable Long projectId,
            @PathVariable Long stepId
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(
                        SuccessCode.OK,
                        projectService.patchProjectCurrentStep(projectId, stepId))
                );
    }
}
