package com.seveneleven.application.adminProject;

import com.seveneleven.dto.GetProjectHistory;
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
}