package com.seveneleven.service;

import com.seveneleven.dto.GetProject;
import com.seveneleven.dto.PaginatedResponse;
import com.seveneleven.dto.PostProject;
import com.seveneleven.dto.PutProject;

public interface AdminProjectService {
    PostProject.Response createProject(PostProject.Request request);
    GetProject.Response getProject(Long id);
    PaginatedResponse<GetProject.Response> getListOfProject(Integer page);
    PutProject.Response updateProject(Long id, PutProject.Request request);
    void deleteProject(Long id);
    String checkProjectNameExists(String projectName);
}
