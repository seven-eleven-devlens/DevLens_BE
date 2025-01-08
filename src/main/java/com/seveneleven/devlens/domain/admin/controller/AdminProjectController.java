package com.seveneleven.devlens.domain.admin.controller;

import com.seveneleven.devlens.domain.admin.dto.PaginatedResponse;
import com.seveneleven.devlens.domain.admin.dto.ProjectDto;
import com.seveneleven.devlens.domain.admin.service.ProjectCreateService;
import com.seveneleven.devlens.domain.admin.service.ProjectReadService;
import com.seveneleven.devlens.global.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-project")
public class AdminProjectController {
    private final ProjectCreateService projectCreateService;
    private final ProjectReadService projectReadService;

    @PostMapping("/new")
    public APIResponse<ProjectDto.ProjectResponse> newProject(
            @RequestBody ProjectDto.ProjectRequest projectRequest
    ) {
        ProjectDto.ProjectResponse project = projectCreateService.createProject(projectRequest);
        return APIResponse.success(project);
    }

    @GetMapping("/list/{id}")
    public APIResponse<ProjectDto.ProjectResponse> readProject(
            @PathVariable Long id
    ){
        ProjectDto.ProjectResponse response = projectReadService.getProject(id);
        return APIResponse.success(response);
    }

    @GetMapping("/list")
    public APIResponse<PaginatedResponse<ProjectDto.ProjectResponse>> listProjects(
            @RequestParam(value = "page", required = true) Integer page
    ) {
        PaginatedResponse<ProjectDto.ProjectResponse> response = projectReadService.getListOfProject(page);
        return APIResponse.success(response);
    }
}
