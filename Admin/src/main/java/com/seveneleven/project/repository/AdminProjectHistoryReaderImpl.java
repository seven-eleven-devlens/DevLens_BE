package com.seveneleven.project.repository;

import com.seveneleven.entity.project.ProjectHistory;
import com.seveneleven.project.exception.ProjectHistoryNotFoundException;
import com.seveneleven.project.service.AdminProjectHistoryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminProjectHistoryReaderImpl implements AdminProjectHistoryReader {
    private final AdminProjectHistoryRepository adminProjectHistoryRepository;
    @Override
    public ProjectHistory getProjectHistory(Long id) {
        return adminProjectHistoryRepository.findById(id).orElseThrow(ProjectHistoryNotFoundException::new);
    }
}
