package com.seveneleven.repository;

import com.seveneleven.entity.project.ProjectType;
import com.seveneleven.service.AdminProjectTypeReader;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminProjectTypeReaderImpl implements AdminProjectTypeReader {
    private final AdminProjectTypeRepository adminProjectTypeRepository;

    @Override
    public ProjectType getProjectType(Long id) {
        return adminProjectTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("프로젝트 타입을 찾을 수 없습니다."));
    }
}
