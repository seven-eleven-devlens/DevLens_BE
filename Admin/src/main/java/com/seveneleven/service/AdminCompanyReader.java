package com.seveneleven.service;

import com.seveneleven.entity.member.Company;

public interface AdminCompanyReader {
    Company getCompany(Long id);
    Company getActiveCompany(Long id);
}
