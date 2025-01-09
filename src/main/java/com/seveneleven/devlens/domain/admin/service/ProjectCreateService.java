package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.common.CheckProjectValidity;
import com.seveneleven.devlens.domain.admin.db.AdminProjectRepository;
import com.seveneleven.devlens.domain.admin.db.PostProjectRequestConverter;
import com.seveneleven.devlens.domain.admin.db.PostProjectResponseConverter;
import com.seveneleven.devlens.domain.admin.dto.PostProject;
import com.seveneleven.devlens.domain.project.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectCreateService {
    private final PostProjectRequestConverter postProjectRequestConverter;
    private final PostProjectResponseConverter postProjectResponseConverter;
    private final AdminProjectRepository projectRepository;
    private final CheckProjectValidity checkProjectValidity;
    private final ProjectHistoryService projectHistoryService;

    public PostProject.Response createProject(PostProject.Request request) {
        checkProjectValidity.checkProjectDuplicatedName(request.getProjectName());
        Project project = projectRepository.save(postProjectRequestConverter.toEntity(request));
        projectHistoryService.saveProjectHistory(project); //이력 저장
        return postProjectResponseConverter.toDTO(project);
    }
}