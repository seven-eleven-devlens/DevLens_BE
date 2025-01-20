package com.seveneleven.service.adminProject;

import com.seveneleven.entity.project.Project;
import com.seveneleven.repository.AdminProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminReaderImpl implements AdminProjectReader{
    private final AdminProjectRepository adminProjectRepository;
    @Override
    public Optional<Project> getProject(Long id) {
        return adminProjectRepository.findById(id);
    }

    @Override
    public Page<Project> findAll(Pageable pageable) {
        return adminProjectRepository.findAll(pageable);
    }
}
