package com.seveneleven.devlens.domain.project.controller;

import com.seveneleven.devlens.domain.project.dto.GetProjectDetail;
import com.seveneleven.devlens.domain.project.dto.GetProjectStep;
import com.seveneleven.devlens.domain.project.service.ProjectService;
import com.seveneleven.devlens.global.entity.YesNo;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/detail")
public class ProjectController implements ProjectDocs {

    private final ProjectService projectService;

    /**
     * 함수명 : getProjectDetail
     * 프로젝트 상세 페이지를 반환하는 함수
     */
    @GetMapping("/{projectId}")
    public APIResponse<GetProjectDetail.Response> getProjectDetail(
            @PathVariable Long projectId
    ) {
        return APIResponse.success(SuccessCode.OK, projectService.getProjectDetail(projectId));
    }
}