package com.seveneleven.project.application;

import com.seveneleven.project.dto.GetProjectHistory;
import com.seveneleven.project.service.AdminProjectHistoryService;
import com.seveneleven.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProjectHistoryFacade {
    private final AdminProjectHistoryService adminProjectHistoryService;

    public GetProjectHistory.Response getProjectHistory(Long id){
        return adminProjectHistoryService.getProjectHistory(id);
    }

    public PaginatedResponse<GetProjectHistory.Response> getListOfProjectHistory(Integer page) {
        return adminProjectHistoryService.getListOfProjectHistory(page);
    }

    public PaginatedResponse<GetProjectHistory.Response> searchHistoryByProjectName(String searchTerm, Integer page) {
        return adminProjectHistoryService.searchHistoryByProjectName(searchTerm,page);
    }
}