package com.seveneleven.devlens.domain.project.service;

import com.seveneleven.devlens.domain.project.dto.GetProjectDetail;
import com.seveneleven.devlens.domain.project.dto.GetProjectList;

public interface ProjectReader {
    GetProjectList.Response getProjectList(Long memberId, Long companyId);
    GetProjectDetail.Response getProjectDetail(Long projectId);
}