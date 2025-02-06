package com.seveneleven.company.application;

import com.seveneleven.company.dto.*;
import com.seveneleven.company.service.AdminCompanyHistoryService;
import com.seveneleven.company.service.AdminCompanyService;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.YN;
import com.seveneleven.member.service.AdminMemberService;
import com.seveneleven.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCompanyFacade {
    private final AdminCompanyService adminCompanyService;
    private final AdminCompanyHistoryService adminCompanyHistoryService;
    private final AdminMemberService adminMemberService;

    public PostCompany.Response registerCompany(PostCompany.Request request) {
        PostCompany.Response response = adminCompanyService.createCompany(request);
        adminCompanyHistoryService.saveHistory(request.toEntity());
        return response;
    }

    public GetCompanyDetail.Response getCompanyDetail(Long id) {
        return adminCompanyService.getCompanyDetail(id);
    }

    public PaginatedResponse<GetCompanies.Response> getCompanyList(Integer page) {
        return adminCompanyService.getListOfCompanies(page);
    }

    public PaginatedResponse<GetCompanies.Response> getCompanyBySearchTerm(String term, Integer page) {
        return adminCompanyService.searchCompaniesByName(term, page);
    }

    public PutCompany.Response updateCompany(Long id, PutCompany.Request request) {
        PutCompany.Response response = adminCompanyService.updateCompany(id, request);
        adminCompanyHistoryService.saveHistory(request.toEntity());
        return response;
    }

    public void changeCompanyIsActive(Long id) {
        Company deletedCompany = adminCompanyService.changeCompanyIsActive(id);
        if(deletedCompany.getIsActive().equals(YN.N)) {
            adminMemberService.deleteCompanyMember(deletedCompany);
        }
        adminCompanyHistoryService.saveHistory(deletedCompany);
    }

    public List<GetAllCompanies> getAllCompanies() {
        return adminCompanyService.getAllCompanies();
    }
}
