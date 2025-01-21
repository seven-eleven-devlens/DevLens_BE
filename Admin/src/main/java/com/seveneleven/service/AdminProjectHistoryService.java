package com.seveneleven.service;

import com.seveneleven.dto.PaginatedResponse;
import com.seveneleven.dto.ReadProjectHistory;

public interface AdminProjectHistoryService {
    void saveProjectHistory(Long id);
    ReadProjectHistory.Response getProjectHistory(Long id);
    PaginatedResponse<ReadProjectHistory.Response> getListOfProjectHistory(Integer page);
    PaginatedResponse<ReadProjectHistory.Response> searchHistoryByProjectName(String searchTerm, Integer page);
}
