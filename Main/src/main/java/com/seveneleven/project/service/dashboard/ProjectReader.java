package com.seveneleven.project.service.dashboard;

import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.GetProjectList;

public interface ProjectReader {
    GetProjectList.Response getProjectList(Long memberId, Long companyId);
    GetProjectDetail.Response getProjectDetail(Long projectId);
}