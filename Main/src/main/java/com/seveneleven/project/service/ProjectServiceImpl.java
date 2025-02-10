package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.GetCompanyProject;
import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.GetProjectList;
import com.seveneleven.project.dto.PatchProjectCurrentStep;
import com.seveneleven.project.service.project.ProjectReader;
import com.seveneleven.project.service.project.ProjectStore;
import com.seveneleven.project.service.step.StepReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectReader projectReader;
    private final ProjectStore projectStore;
    private final StepReader stepReader;

    @Override
    public Project getProject(Long projectId) {
        return projectReader.read(projectId);
    }

    @Override
    public GetProjectList.Response getMyProjectList(Long memberId, String projectStatusCode) {
        return projectReader.getMyProjectList(memberId, projectStatusCode);
    }

    @Override
    public GetCompanyProject.Response getCompanyProject(Long companyId) {
        return projectReader.getCompanyProject(companyId);
    }

    @Override
    public GetProjectDetail.Response getProjectDetail(Long projectId) {
        return projectReader.getProjectDetail(projectId);
    }

    @Override
    public PatchProjectCurrentStep.Response patchProjectCurrentStep(Long projectId, Long stepId) {
        Project project = getProject(projectId);
        ProjectStep projectStep = stepReader.read(stepId);

        return PatchProjectCurrentStep.Response.toDto(
                projectStore.modifyProjectCurrentStep(project, projectStep)
        );
    }
}
