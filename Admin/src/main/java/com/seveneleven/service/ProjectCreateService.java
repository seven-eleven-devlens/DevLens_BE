package com.seveneleven.service;

import com.seveneleven.common.CheckProjectValidity;
import com.seveneleven.dto.PostProject;
import com.seveneleven.entity.project.Project;
import com.seveneleven.repository.AdminProjectRepository;
import com.seveneleven.repository.PostProjectRequestConverter;
import com.seveneleven.repository.PostProjectResponseConverter;
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