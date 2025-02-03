package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.CheckResult;

public interface CheckResultReader {
    CheckResult read(Long applicationId);
}
