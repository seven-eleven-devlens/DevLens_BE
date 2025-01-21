package com.seveneleven.application.adminProject;

import com.seveneleven.dto.GetProject;
import com.seveneleven.dto.PostProject;
import com.seveneleven.entity.project.Project;
import com.seveneleven.repository.PostProjectRequestConverter;
import com.seveneleven.service.AdminProjectHistoryService;
import com.seveneleven.service.adminProject.AdminProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProjectFacade {
    private final AdminProjectService adminProjectService;
    private final AdminProjectHistoryService adminProjectHistoryService;
    private final PostProjectRequestConverter postProjectRequestConverter;

    public PostProject.Response registerProject(PostProject.Request request){
        Project project = postProjectRequestConverter.toEntity(request);
        //이력 저장
        adminProjectHistoryService.saveProjectHistory(project);
        return adminProjectService.createProject(request);
    }

    public GetProject.Response getProject(Long id){
        return adminProjectService.getProject(id);
    }
}
