package com.seveneleven.service;

import com.seveneleven.dto.GetProjectHistory;
import com.seveneleven.entity.project.Project;
import com.seveneleven.response.PaginatedResponse;

public interface AdminProjectHistoryService {
    GetProjectHistory.Response getProjectHistory(Long id);
    PaginatedResponse<GetProjectHistory.Response> getListOfProjectHistory(Integer page);
    PaginatedResponse<GetProjectHistory.Response> searchHistoryByProjectName(String searchTerm, Integer page);
    void saveProjectHistory(Project project);
}