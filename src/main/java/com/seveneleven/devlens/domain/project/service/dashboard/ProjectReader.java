package com.seveneleven.devlens.domain.project.service.dashboard;

import com.seveneleven.devlens.domain.project.dto.GetProjectList;

public interface ProjectReader {
    GetProjectList.Response getProjectList(Long memberId, Long companyId);
}