package com.seveneleven.devlens.domain.project.service;

import com.seveneleven.devlens.domain.project.dto.GetStepChecklist;

public interface ProjectChecklistService {
    GetStepChecklist.Response getStepChecklist(Long stepId);
}
