package com.seveneleven.service;

import com.seveneleven.entity.project.ProjectType;

public interface AdminProjectTypeReader {
    ProjectType getProjectType(Long id);
}
