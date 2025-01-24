package com.seveneleven.service;

import com.seveneleven.entity.project.ProjectHistory;

public interface AdminProjectHistoryStore {
    void store(ProjectHistory projectHistory);
}
