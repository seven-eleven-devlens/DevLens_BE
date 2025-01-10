package com.seveneleven.devlens.domain.project.service.checklist;

import com.seveneleven.devlens.domain.project.dto.GetStepChecklist;

public interface ChecklistReader {
    GetStepChecklist.Response getStepChecklist(Long stepId);
}
