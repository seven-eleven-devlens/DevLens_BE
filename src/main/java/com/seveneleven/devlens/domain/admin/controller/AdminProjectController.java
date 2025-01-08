package com.seveneleven.devlens.domain.admin.controller;

import com.seveneleven.devlens.domain.admin.dto.PaginatedResponse;
import com.seveneleven.devlens.domain.admin.dto.ProjectDto;
import com.seveneleven.devlens.domain.admin.dto.ProjectHistoryDto;
import com.seveneleven.devlens.domain.admin.service.ProjectCreateService;
import com.seveneleven.devlens.domain.admin.service.ProjectHistoryService;
import com.seveneleven.devlens.domain.admin.service.ProjectReadService;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-project")
public class AdminProjectController {
    private final ProjectCreateService projectCreateService;
    private final ProjectReadService projectReadService;
    private final ProjectHistoryService projectHistoryService;

    /*
        함수명 : newProject
        함수 목적 : 프로젝트 생성
     */
    @PostMapping("/new")
    public ResponseEntity<APIResponse<ProjectDto.ProjectResponse>> newProject(
            @RequestBody ProjectDto.ProjectRequest projectRequest
    ) {
        ProjectDto.ProjectResponse project = projectCreateService.createProject(projectRequest);
        return ResponseEntity
                .status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED, project));
    }

    /*
        함수명 : readProject
        함수 목적 : 프로젝트 조회
     */
    @GetMapping("/list/{id}")
    public ResponseEntity<APIResponse<ProjectDto.ProjectResponse>> readProject(
            @PathVariable Long id
    ) {
        ProjectDto.ProjectResponse response = projectReadService.getProject(id);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK,response));
    }

    /*
        함수명 : getListOfProjects
        함수 목적 : 프로젝트 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<APIResponse<PaginatedResponse<ProjectDto.ProjectResponse>>> getListOfProjects(
            @RequestParam(value = "page", required = true) Integer page
    ) {
        PaginatedResponse<ProjectDto.ProjectResponse> response = projectReadService.getListOfProject(page);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK,response));
    }

    /*
        함수명 : getListOfProjectHistory
        함수 목적 : 프로젝트 이력 목록 조회
     */
    @GetMapping("/history/list")
    public ResponseEntity<APIResponse<PaginatedResponse<ProjectHistoryDto.ProjectHistoryResponse>>> getListOfProjectHistory(
            @RequestParam(value = "page") Integer page
    ) {
        PaginatedResponse<ProjectHistoryDto.ProjectHistoryResponse> response = projectHistoryService.getListOfProjectHistory(page);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK,response));
    }

    /*
        함수명 : getProjectHistory
        함수 목적 : 프로젝트 이력 조회
     */
    @GetMapping("/history/list/{id}")
    public ResponseEntity<APIResponse<ProjectHistoryDto.ProjectHistoryResponse>> getProjectHistory(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK,projectHistoryService.getProjectHistory(id)));
    }
}
