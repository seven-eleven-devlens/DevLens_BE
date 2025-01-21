package com.seveneleven.service;

import com.seveneleven.entity.project.ProjectHistory;

import java.util.Optional;

public interface AdminProjectHistoryReader {
    Optional<ProjectHistory> getProjectHistory(Long id);
}