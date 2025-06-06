package com.seveneleven.project.application;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.ProjectTag;
import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.*;
import com.seveneleven.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProjectFacade {
    private final AdminProjectService adminProjectService;
    private final AdminProjectHistoryService adminProjectHistoryService;
    private final AdminProjectStepService adminProjectStepService;
    private final AdminProjectTagService adminProjectTagService;
    private final AdminProjectAuthorizationService adminProjectAuthorizationService;

    @Transactional
    public PostProject.Response registerProject(PostProject.Request request){
        Project project = adminProjectService.createProject(request);
        adminProjectStepService.createBasicStep(project);
        adminProjectHistoryService.saveProjectHistory(project);
        List<ProjectTag> projectTags = adminProjectTagService.storeProjectTags(project, request.getProjectTags());
        List<ProjectAuthorization> authorizations = adminProjectAuthorizationService.store(project, request.getCustomerAuthorizations(), request.getDeveloperAuthorizations());
        return PostProject.Response.of(project, projectTags, authorizations);
    }

    @Transactional
    public PutProject.Response updateProject(Long id, PutProject.Request request){
        Project project = adminProjectService.updateProject(id, request);
        adminProjectHistoryService.saveProjectHistory(project);
        List<ProjectTag> projectTags = adminProjectTagService.storeProjectTags(project, request.getProjectTags());
        List<ProjectAuthorization> authorizations =
                adminProjectAuthorizationService.store(
                        project,
                        request.getCustomerAuthorizations(),
                        request.getDeveloperAuthorizations()
                );
        return PutProject.Response.of(project, projectTags, authorizations);
    }

    public GetProject.Response getProject(Long id){
        Project project = adminProjectService.getProject(id);
        List<ProjectAuthorization> authorizations = adminProjectAuthorizationService.readByProjectId(id);
        List<ProjectTag> projectTags = adminProjectTagService.getAllProjectTags(id);
        return GetProject.Response.of(project, projectTags, authorizations);
    }

    public PaginatedResponse<GetProjectList.Response> getListOfProject(Integer page) {
        return adminProjectService.getListOfProject(page);
    }

    public void deleteProject(Long id){
        Project project = adminProjectService.deleteProject(id);
        adminProjectHistoryService.saveProjectHistory(project);
    }

    public String checkProjectNameExists(String name){
        return adminProjectService.checkProjectNameExists(name);
    }

    public PaginatedResponse<GetProjectList.Response> getCompanyProject(Long id, Integer page) {
        return adminProjectService.getCompanyProject(page, id);
    }

    @Transactional(readOnly = true)
    public List<GetAdminDashboard.Response> getAdminDashboard() {
        List<Project> processingProjects = adminProjectService.getProcessingProjects();

        return processingProjects.stream().map(project ->
            GetAdminDashboard.Response.toDto(
                    project,
                    adminProjectTagService.getAllProjectTags(project.getId())
            )
        ).toList();
    }
}
