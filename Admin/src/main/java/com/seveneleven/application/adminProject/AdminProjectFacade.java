package com.seveneleven.application.adminProject;

import com.seveneleven.dto.GetProject;
import com.seveneleven.dto.PostProject;
import com.seveneleven.service.adminProject.AdminProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProjectFacade {
    private final AdminProjectService adminProjectService;

    public PostProject.Response registerProject(PostProject.Request request){
        return adminProjectService.createProject(request);
    }

    public GetProject.Response getProject(Long id){
        return adminProjectService.getProject(id);
    }
}
