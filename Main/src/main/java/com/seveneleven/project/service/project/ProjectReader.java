package com.seveneleven.project.service.project;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.GetCompanyProject;
import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.GetProjectList;

public interface ProjectReader {
    Project read(Long projectId);
    GetProjectList.Response getMyProjectList(Long memberId);
    GetCompanyProject.Response getCompanyProject(Long companyId);
    GetProjectDetail.Response getProjectDetail(Long projectId);
}