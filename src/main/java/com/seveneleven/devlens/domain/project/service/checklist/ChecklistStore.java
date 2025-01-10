package com.seveneleven.devlens.domain.project.service.checklist;

import com.seveneleven.devlens.domain.project.dto.PostProjectChecklist;
import com.seveneleven.devlens.domain.project.dto.PutProjectChecklist;
import com.seveneleven.devlens.domain.project.entity.Checklist;

public interface ChecklistStore {
    Checklist storeChecklist(PostProjectChecklist.Request checklist);
}
