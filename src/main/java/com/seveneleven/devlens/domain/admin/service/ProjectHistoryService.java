package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.db.ProjectHistoryConverter;
import com.seveneleven.devlens.domain.admin.db.ProjectHistoryRepository;
import com.seveneleven.devlens.domain.admin.db.ProjectRepository;
import com.seveneleven.devlens.domain.admin.dto.PaginatedResponse;
import com.seveneleven.devlens.domain.admin.dto.ProjectHistoryDto;
import com.seveneleven.devlens.domain.admin.exception.ProjectNotFoundException;
import com.seveneleven.devlens.domain.project.entity.Project;
import com.seveneleven.devlens.domain.project.entity.ProjectHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectHistoryService {
    private final ProjectRepository projectRepository;
    private final ProjectHistoryRepository projectHistoryRepository;
    private final ProjectHistoryConverter projectHistoryConverter;
    private final int pageSize = 10;

    public void saveProjectHistory(Project project) {
        // 프로젝트 저장
        projectRepository.save(project);

        // 이력 저장
        ProjectHistory projectHistory = new ProjectHistory(
                project.getProjectName(),
                project.getCustomer().getCompanyName(),
                project.getDeveloper().getCompanyName(),
                project.getProjectDescription(),
                project.getProjectStatusCode().name(),
                project.getProjectTypeId().getProjectTypeName(),
                project.getBnsManager().getId(),
                project.getHasImage(),
                project.getContractNumber(),
                project.getPlannedStartDate(),
                project.getStartDate(),
                project.getPlannedEndDate(),
                project.getEndDate()
        );

        projectHistoryRepository.save(projectHistory);
    }

    public PaginatedResponse<ProjectHistoryDto.ProjectHistoryResponse> getListOfProjectHistory(
            Integer page, Integer id
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProjectHistory> projectHistories = projectHistoryRepository.findAll(pageable);
        if(projectHistories.getContent().isEmpty()) {
            throw new ProjectNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(projectHistories.map(projectHistoryConverter::toDTO));
    }
}
