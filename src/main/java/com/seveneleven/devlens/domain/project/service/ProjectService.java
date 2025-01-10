package com.seveneleven.devlens.domain.project.service;

import com.seveneleven.devlens.domain.project.dto.GetProjectDetail;
import com.seveneleven.devlens.domain.project.dto.GetProjectList;

public interface ProjectService {
    GetProjectList.Response getProjectList(Long memberId);
    GetProjectDetail.Response getProjectDetail(Long projectId);
}
