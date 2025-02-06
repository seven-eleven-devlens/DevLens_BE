package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.GetCompanyProject;
import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.GetProjectList;
import com.seveneleven.project.dto.PatchProjectCurrentStep;

public interface ProjectService {
    Project getProject(Long projectId);
    GetProjectList.Response getMyProjectList(Long memberId, String step);
    GetCompanyProject.Response getCompanyProject(Long companyId);
    GetProjectDetail.Response getProjectDetail(Long projectId);
    PatchProjectCurrentStep.Response patchProjectCurrentStep(Long projectId, Long stepId);
}
