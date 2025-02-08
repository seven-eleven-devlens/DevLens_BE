package com.seveneleven.project.controller;

import com.seveneleven.project.dto.GetCompanyProject;
import com.seveneleven.project.dto.GetProjectList;
import com.seveneleven.project.service.ProjectService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity
                .status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(SuccessCode.OK, projectService.getMyProjectList(userDetails.getMember().getId())));
    }

    @GetMapping("/companies/{companyId}/projects")
    public ResponseEntity<APIResponse<GetCompanyProject.Response>> getMyCompanyProject(
            @PathVariable Long companyId
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(SuccessCode.OK, projectService.getCompanyProject(companyId)));
    }
}
