package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.db.AdminProjectHistoryRepository;
import com.seveneleven.devlens.domain.admin.db.ProjectHistoryConverter;
import com.seveneleven.devlens.domain.admin.dto.PaginatedResponse;
import com.seveneleven.devlens.domain.admin.dto.ReadProjectHistory;
import com.seveneleven.devlens.domain.admin.exception.ProjectHistoryNotFoundException;
import com.seveneleven.devlens.domain.project.entity.Project;
import com.seveneleven.devlens.domain.project.entity.ProjectHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProjectHistoryService {
    private final AdminProjectHistoryRepository projectHistoryRepository;
    private final ProjectHistoryConverter projectHistoryConverter;
    private final int PAGE_SIZE = 10;

    public void saveProjectHistory(Project project) {
        // 이력 저장
        ProjectHistory projectHistory = new ProjectHistory(project);
        projectHistoryRepository.save(projectHistory);
    }

    @Transactional(readOnly = true)
    public ReadProjectHistory.Response getProjectHistory(
            Long id
    ) {
        return projectHistoryRepository.findById(id)
                .map(projectHistoryConverter::toDTO)
                .orElseThrow(ProjectHistoryNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<ReadProjectHistory.Response> getListOfProjectHistory(
            Integer page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id").descending());
        Page<ProjectHistory> projectHistories = projectHistoryRepository.findAll(pageable);
        if (projectHistories.getContent().isEmpty()) {
            throw new ProjectHistoryNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(projectHistories.map(projectHistoryConverter::toDTO));
    }
}
