package com.seveneleven.service;

import com.seveneleven.entity.project.ProjectHistory;

public interface AdminProjectHistoryReader {
    ProjectHistory getProjectHistory(Long id);
}