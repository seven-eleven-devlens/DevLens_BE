package com.seveneleven.application.adminProject;

import com.seveneleven.dto.GetProject;
import com.seveneleven.dto.PaginatedResponse;
import com.seveneleven.dto.PostProject;
import com.seveneleven.dto.PutProject;
import com.seveneleven.service.AdminProjectHistoryService;
import com.seveneleven.service.AdminProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProjectFacade {
    private final AdminProjectService adminProjectService;
    private final AdminProjectHistoryService adminProjectHistoryService;

    public PostProject.Response registerProject(PostProject.Request request){
        PostProject.Response response = adminProjectService.createProject(request);
        adminProjectHistoryService.saveProjectHistory(response.getId());
        return response;
    }

    public PutProject.Response updateProject(Long id,PutProject.Request request){
        PutProject.Response response = adminProjectService.updateProject(id, request);
        adminProjectHistoryService.saveProjectHistory(request.getId());
        return response;
    }

    public GetProject.Response getProject(Long id){
        return adminProjectService.getProject(id);
    }

    public PaginatedResponse<GetProject.Response> getListOfProject(Integer page) {
        return adminProjectService.getListOfProject(page);
    }

    public void deleteProject(Long id){
        adminProjectService.deleteProject(id);
    }
}
