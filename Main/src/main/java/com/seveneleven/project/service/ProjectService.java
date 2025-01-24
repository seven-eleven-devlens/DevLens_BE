package com.seveneleven.project.service;

import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.GetProjectList;

public interface ProjectService {
    GetProjectList.Response getProjectList(Long memberId);
//    GetProjectDetail.Response getProjectDetail(Long projectId);
}
