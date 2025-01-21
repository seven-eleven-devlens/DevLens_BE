package com.seveneleven.service;

import com.seveneleven.entity.project.Project;

public interface AdminProjectStore {
    Project store(Project initProject);
    void delete(Long id);
}