package com.seveneleven.controller;

import com.seveneleven.dto.*;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.service.ProjectCreateService;
import com.seveneleven.service.ProjectHistoryService;
import com.seveneleven.service.ProjectReadService;
import com.seveneleven.service.ProjectUpdateService;
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
    private final ProjectUpdateService projectUpdateService;

    /*
        함수명 : newProject
        함수 목적 : 프로젝트 생성
     */
    @PostMapping("")
    public ResponseEntity<APIResponse<PostProject.Response>> newProject(
            @RequestBody PostProject.Request request
    ) {
        PostProject.Response project = projectCreateService.createProject(request);
        return ResponseEntity
                .status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED, project));
    }

    /*
        함수명 : readProject
        함수 목적 : 프로젝트 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<GetProject.Response>> readProject(
            @PathVariable Long id
    ) {
        GetProject.Response response = projectReadService.getProject(id);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, response));
    }

    /*
        함수명 : getListOfProjects
        함수 목적 : 프로젝트 목록 조회
     */
    @GetMapping("")
    public ResponseEntity<APIResponse<PaginatedResponse<GetProject.Response>>> getListOfProjects(
            @RequestParam(value = "page") Integer page
    ) {
        PaginatedResponse<GetProject.Response> response = projectReadService.getListOfProject(page);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, response));
    }

    /*
        함수명 : getListOfProjectHistory
        함수 목적 : 프로젝트 이력 목록 조회
     */
    @GetMapping("/histories")
    public ResponseEntity<APIResponse<PaginatedResponse<ReadProjectHistory.Response>>> getListOfProjectHistory(
            @RequestParam(value = "page") Integer page
    ) {
        PaginatedResponse<ReadProjectHistory.Response> response = projectHistoryService.getListOfProjectHistory(page);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, response));
    }

    /*
        함수명 : getProjectHistory
        함수 목적 : 프로젝트 이력 조회
     */
    @GetMapping("/histories/{id}")
    public ResponseEntity<APIResponse<ReadProjectHistory.Response>> getProjectHistory(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, projectHistoryService.getProjectHistory(id)));
    }
    /*
        함수명 : updateProject
        함수 목적 : 프로젝트 이력 조회
     */
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<PutProject.Response>> updateProject(
            @PathVariable Long id,
            @RequestBody PutProject.Request request
    ){
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK,projectUpdateService.updateProject(id, request)));
    }
    @GetMapping("/histories/search")
    public ResponseEntity<APIResponse<PaginatedResponse<ReadProjectHistory.Response>>> searchHistories(
            @RequestParam String searchTerm,
            @RequestParam Integer page
    ){
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK,projectHistoryService.searchHistoryByProjectName(searchTerm, page)));
    }
    /*
        함수명 : deleteProject
        함수 목적 : 프로젝트 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteProject(
            @PathVariable Long id
    ){
        projectUpdateService.deleteProject(id);
        return ResponseEntity
                .status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }
}
