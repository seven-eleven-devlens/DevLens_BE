package com.seveneleven.service;

import com.seveneleven.dto.PaginatedResponse;
import com.seveneleven.dto.ReadProjectHistory;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectHistory;
import com.seveneleven.exception.ProjectHistoryNotFoundException;
import com.seveneleven.repository.AdminProjectHistoryRepository;
import com.seveneleven.repository.ProjectHistoryConverter;
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
