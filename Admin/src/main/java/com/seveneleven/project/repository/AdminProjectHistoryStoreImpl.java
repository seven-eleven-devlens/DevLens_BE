package com.seveneleven.project.repository;

import com.seveneleven.entity.project.ProjectHistory;
import com.seveneleven.project.service.AdminProjectHistoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminProjectHistoryStoreImpl implements AdminProjectHistoryStore {
    private final AdminProjectHistoryRepository adminProjectHistoryRepository;
    @Override
    public void store(ProjectHistory projectHistory) {
        adminProjectHistoryRepository.save(projectHistory);
    }
}
