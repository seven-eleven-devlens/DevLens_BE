package com.seveneleven.devlens.domain.project.service;

import com.seveneleven.devlens.domain.project.dto.GetStepChecklist;
import com.seveneleven.devlens.domain.project.dto.PostProjectChecklist;

public interface ProjectChecklistService {
    GetStepChecklist.Response getStepChecklist(Long stepId);
    PostProjectChecklist.Response postProjectChecklist(PostProjectChecklist.Request postProjectChecklist);
}
