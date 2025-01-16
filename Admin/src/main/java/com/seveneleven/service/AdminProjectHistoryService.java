package com.seveneleven.service;

import com.seveneleven.dto.PaginatedResponse;
import com.seveneleven.dto.ReadProjectHistory;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectHistory;
import com.seveneleven.exception.ProjectHistoryNotFoundException;
import com.seveneleven.repository.AdminMemberRepository;
import com.seveneleven.repository.AdminProjectHistoryRepository;
import com.seveneleven.repository.ProjectHistoryConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.seveneleven.common.PageSize.DEFAULT_PAGE_SIZE;

@RequiredArgsConstructor
@Service
public class AdminProjectHistoryService {
    private final AdminProjectHistoryRepository projectHistoryRepository;
    private final ProjectHistoryConverter projectHistoryConverter;
    private final AdminMemberRepository adminMemberRepository;

    public void saveProjectHistory(Project project) {
        // 이력 저장
        projectHistoryRepository.save(project.saveHistory());
    }

    @Transactional(readOnly = true)
    public ReadProjectHistory.Response getProjectHistory(Long id) {
        ProjectHistory projectHistory = projectHistoryRepository.findById(id).orElseThrow(ProjectHistoryNotFoundException::new);
        String createdBy = adminMemberRepository.findById(projectHistory.getCreatedBy()).map(Member::getName).orElse(null);
        String updatedBy = adminMemberRepository.findById(projectHistory.getUpdatedBy()).map(Member::getName).orElse(null);
        return projectHistoryConverter.toDTO(projectHistory, createdBy, updatedBy);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<ReadProjectHistory.Response> getListOfProjectHistory(Integer page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE.getPageSize(), Sort.by("id").descending());
        Page<ProjectHistory> projectHistories = projectHistoryRepository.findAll(pageable);
        if (projectHistories.getContent().isEmpty()) {
            throw new ProjectHistoryNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(
                projectHistories.map(projectHistory -> {
                    String createdBy = adminMemberRepository.findById(projectHistory.getCreatedBy()).map(Member::getName).orElse(null);
                    String updatedBy = adminMemberRepository.findById(projectHistory.getUpdatedBy()).map(Member::getName).orElse(null);
                    return projectHistoryConverter.toDTO(projectHistory, createdBy, updatedBy);
                })
        );
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<ReadProjectHistory.Response> searchHistoryByProjectName(String searchTerm, Integer page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE.getPageSize(), Sort.by("projectName").ascending());
        Page<ProjectHistory> historyPage = projectHistoryRepository.findByProjectNameContainingIgnoreCase(searchTerm ,pageable);
        return PaginatedResponse.createPaginatedResponse(historyPage.map(projectHistoryConverter::toDTO));
    }
}
