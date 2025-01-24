package com.seveneleven.application.adminProject;

import com.seveneleven.dto.GetProjectHistory;
import com.seveneleven.response.PaginatedResponse;
import com.seveneleven.service.AdminProjectHistoryService;
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