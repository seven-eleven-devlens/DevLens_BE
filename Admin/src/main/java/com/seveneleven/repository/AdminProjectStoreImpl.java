package com.seveneleven.repository;

import com.seveneleven.entity.project.Project;
import com.seveneleven.exception.ProjectNotFoundException;
import com.seveneleven.service.AdminProjectStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminProjectStoreImpl implements AdminProjectStore {
    private final AdminProjectRepository adminProjectRepository;
    @Override
    public Project store(Project initProject) {
        return adminProjectRepository.save(initProject);
    }

    @Override
    public void delete(Long id) {
        Project project = adminProjectRepository.findByIdAndProjectStatusCodeNot(id, Project.ProjectStatusCode.CANCELLED).orElseThrow(ProjectNotFoundException::new);
        project.delete();
    }
}