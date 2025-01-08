package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.common.CheckProjectValidity;
import com.seveneleven.devlens.domain.admin.db.ProjectHistoryRepository;
import com.seveneleven.devlens.domain.admin.db.ProjectRepository;
import com.seveneleven.devlens.domain.admin.db.ProjectRequestConverter;
import com.seveneleven.devlens.domain.admin.db.ProjectResponseConverter;
import com.seveneleven.devlens.domain.admin.dto.ProjectDto;
import com.seveneleven.devlens.domain.project.entity.Project;
import com.seveneleven.devlens.domain.project.entity.ProjectHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectCreateService {
    private final ProjectRequestConverter projectRequestConverter;
    private final ProjectResponseConverter projectResponseConverter;
    private final ProjectRepository projectRepository;
    private final CheckProjectValidity checkProjectValidity;
    private final ProjectHistoryRepository projectHistoryRepository;

    public ProjectDto.ProjectResponse createProject(ProjectDto.ProjectRequest request) {
        checkProjectValidity.checkProjectDuplicatedName(request.getProjectName());
        Project project = projectRepository.save(projectRequestConverter.toEntity(request));
        saveProjectHistory(project); //이력 저장
        return projectResponseConverter.toDTO(project);
    }

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
}
