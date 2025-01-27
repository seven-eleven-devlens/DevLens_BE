package com.seveneleven.application.adminCompany;

import com.seveneleven.dto.PostCompany;
import com.seveneleven.service.AdminCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCompanyFacade {
    private final AdminCompanyService adminCompanyService;
    public PostCompany.Response registerCompany(PostCompany.Request request) {
        return adminCompanyService.createCompany(request);
    }
}
