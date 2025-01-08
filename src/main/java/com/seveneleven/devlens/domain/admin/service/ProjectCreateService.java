package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.common.CheckProjectValidity;
import com.seveneleven.devlens.domain.admin.db.ProjectRepository;
import com.seveneleven.devlens.domain.admin.db.ProjectRequestConverter;
import com.seveneleven.devlens.domain.admin.db.ProjectResponseConverter;
import com.seveneleven.devlens.domain.admin.dto.ProjectDto;
import com.seveneleven.devlens.domain.project.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectCreateService {
    private final ProjectRequestConverter projectRequestConverter;
    private final ProjectResponseConverter projectResponseConverter;
    private final ProjectRepository projectRepository;
    private final CheckProjectValidity checkProjectValidity;
    private final ProjectHistoryService projectHistoryService;

    public ProjectDto.ProjectResponse createProject(ProjectDto.ProjectRequest request) {
        checkProjectValidity.checkProjectDuplicatedName(request.getProjectName());
        Project project = projectRepository.save(projectRequestConverter.toEntity(request));
        projectHistoryService.saveProjectHistory(project); //이력 저장
        return projectResponseConverter.toDTO(project);
    }
}