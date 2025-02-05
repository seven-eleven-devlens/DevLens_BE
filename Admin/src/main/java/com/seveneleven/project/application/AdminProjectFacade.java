package com.seveneleven.project.application;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.GetProject;
import com.seveneleven.project.dto.PostProject;
import com.seveneleven.project.dto.PutProject;
import com.seveneleven.project.service.AdminProjectHistoryService;
import com.seveneleven.project.service.AdminProjectService;
import com.seveneleven.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProjectFacade {
    private final AdminProjectService adminProjectService;
    private final AdminProjectHistoryService adminProjectHistoryService;

    public PostProject.Response registerProject(PostProject.Request request){
        Project project = adminProjectService.createProject(request);
        adminProjectHistoryService.saveProjectHistory(project);
        return PostProject.Response.of(project);
    }

    public PutProject.Response updateProject(Long id,PutProject.Request request){
        Project project = adminProjectService.updateProject(id, request);
        adminProjectHistoryService.saveProjectHistory(project);
        return PutProject.Response.of(project);
    }

    public GetProject.Response getProject(Long id){
        return adminProjectService.getProject(id);
    }

    public PaginatedResponse<GetProject.Response> getListOfProject(Integer page) {
        return adminProjectService.getListOfProject(page);
    }

    public void deleteProject(Long id){
        Project project = adminProjectService.deleteProject(id);
        adminProjectHistoryService.saveProjectHistory(project);
    }

    public String checkProjectNameExists(String name){
        return adminProjectService.checkProjectNameExists(name);
    }

    public PaginatedResponse<GetProject.Response> getCompanyProject(Long id, Integer page) {
        return adminProjectService.getCompanyProject(page, id);
    }
}
