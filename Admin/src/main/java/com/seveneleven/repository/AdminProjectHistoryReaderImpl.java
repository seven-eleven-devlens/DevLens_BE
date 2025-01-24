package com.seveneleven.repository;

import com.seveneleven.entity.project.ProjectHistory;
import com.seveneleven.exception.ProjectHistoryNotFoundException;
import com.seveneleven.service.AdminProjectHistoryReader;
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
