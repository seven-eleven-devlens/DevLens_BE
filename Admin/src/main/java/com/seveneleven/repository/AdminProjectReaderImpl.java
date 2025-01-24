package com.seveneleven.repository;

import com.seveneleven.entity.project.Project;
import com.seveneleven.exception.ProjectNotFoundException;
import com.seveneleven.service.AdminProjectReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminProjectReaderImpl implements AdminProjectReader {
    private final AdminProjectRepository adminProjectRepository;
    @Override
    public Project getProject(Long id) {
        return adminProjectRepository.findByIdAndProjectStatusCodeNot(id, Project.ProjectStatusCode.DELETED).orElseThrow(ProjectNotFoundException::new);
    }

    @Override
    public Page<Project> findAll(Pageable pageable) {
        return adminProjectRepository.findAllByProjectStatusCodeNot(pageable, Project.ProjectStatusCode.DELETED);
    }

    @Override
    public boolean checkProjectExists(String name) {
        return adminProjectRepository.findByProjectNameAndProjectStatusCodeNot(name, Project.ProjectStatusCode.DELETED)
                .isPresent();
    }
}