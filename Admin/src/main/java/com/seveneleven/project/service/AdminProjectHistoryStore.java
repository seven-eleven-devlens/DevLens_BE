package com.seveneleven.project.service;

import com.seveneleven.entity.project.ProjectHistory;

public interface AdminProjectHistoryStore {
    void store(ProjectHistory projectHistory);
}
