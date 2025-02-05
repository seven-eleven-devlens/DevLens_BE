package com.seveneleven.project.repository;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.service.AdminProjectStore;
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
    public Project delete(Project project) {
        return project.delete();
    }
}