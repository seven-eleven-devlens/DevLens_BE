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

    /**
     * 함수명 : getProjectStep
     * 해당 프로젝트의 단계들을 반환하는 함수
     */
    @GetMapping("/step/{projectId}")
    public APIResponse<GetProjectStep.Response> getProjectStep(
            @PathVariable Long projectId
    ) {
        List<GetProjectStep.ProjectChecklist> checklists1 = List.of(
                new GetProjectStep.ProjectChecklist(
                        101L,                      // checklistId
                        "Checklist Item 1",        // checklistName
                        YesNo.YES,                       // checklistStatus
                        LocalDateTime.now()
                ),
                new GetProjectStep.ProjectChecklist(
                        102L,                      // checklistId
                        "Checklist Item 2",        // checklistName
                        YesNo.NO,                       // checklistStatus
                        LocalDateTime.now()
                )
        );

        List<GetProjectStep.ProjectChecklist> checklists2 = List.of(
                new GetProjectStep.ProjectChecklist(
                        201L,                      // checklistId
                        "Checklist Item A",        // checklistName
                        YesNo.YES,                       // checklistStatus
                        LocalDateTime.now()
                ),
                new GetProjectStep.ProjectChecklist(
                        202L,                      // checklistId
                        "Checklist Item B",        // checklistName
                        YesNo.NO,                       // checklistStatus
                        LocalDateTime.now()
                )
        );

        List<GetProjectStep.ProjectStepInfo> steps = List.of(
                new GetProjectStep.ProjectStepInfo(
                        1L,                        // stepId
                        "Step 1",                  // stepName
                        checklists1                // projectChecklist
                ),
                new GetProjectStep.ProjectStepInfo(
                        2L,                        // stepId
                        "Step 2",                  // stepName
                        checklists2                // projectChecklist
                )
        );

        return APIResponse.success(SuccessCode.OK, new GetProjectStep.Response(projectId,steps));
    }
}