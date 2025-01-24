package com.seveneleven.service;

import com.seveneleven.entity.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminProjectReader {
    Project getProject(Long id);
    Page<Project> findAll(Pageable pageable);
    boolean checkProjectExists(String name);
}
