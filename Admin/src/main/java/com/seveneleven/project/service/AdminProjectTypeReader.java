package com.seveneleven.project.service;

import com.seveneleven.entity.project.ProjectType;

public interface AdminProjectTypeReader {
    ProjectType getProjectType(Long id);
}
