package com.seveneleven.service;

import com.seveneleven.dto.GetProjectHistory;
import com.seveneleven.dto.PaginatedResponse;

public interface AdminProjectHistoryService {
    void saveProjectHistory(Long id);
    GetProjectHistory.Response getProjectHistory(Long id);
    PaginatedResponse<GetProjectHistory.Response> getListOfProjectHistory(Integer page);
    PaginatedResponse<GetProjectHistory.Response> searchHistoryByProjectName(String searchTerm, Integer page);
}