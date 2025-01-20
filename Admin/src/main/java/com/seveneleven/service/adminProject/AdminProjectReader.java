package com.seveneleven.service.adminProject;

import com.seveneleven.entity.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdminProjectReader {
    Optional<Project> getProject(Long id);
    Page<Project> findAll(Pageable pageable);
}
