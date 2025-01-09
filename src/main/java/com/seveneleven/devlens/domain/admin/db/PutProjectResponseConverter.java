package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.admin.common.EntityConverter;
import com.seveneleven.devlens.domain.admin.dto.PutProject;
import com.seveneleven.devlens.domain.admin.exception.CompanyNotFoundException;
import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.project.entity.Project;
import com.seveneleven.devlens.domain.project.entity.ProjectType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PutProjectResponseConverter implements EntityConverter<PutProject.Response, Project> {
    private final AdminMemberRepository adminMemberRepository;
    private final CompanyRepository companyRepository;
    private final AdminProjectTypeRepository adminProjectTypeRepository;
    @Override
    public PutProject.Response toDTO(Project project) {
        //미사용 메서드
        return null;
    }

    @Override
    public Project toEntity(PutProject.Response response) {
        Company customer = companyRepository.findById(response.getId()).orElseThrow(CompanyNotFoundException::new);
        Company developer = companyRepository.findById(response.getId()).orElseThrow(CompanyNotFoundException::new);
        Member bnsManager = adminMemberRepository.findById(response.getId()).orElseThrow(EntityNotFoundException::new);
        ProjectType projectType = adminProjectTypeRepository.findById(response.getId()).orElseThrow(EntityNotFoundException::new);
        return new Project(
                response.getId(),
                response.getProjectName(),
                customer,
                developer,
                response.getProjectDescription(),
                projectType,
                response.getProjectStatusCode(),
                bnsManager,
                response.getHasImage(),
                response.getContractNumber(),
                response.getPlannedStartDate(),
                response.getPlannedEndDate(),
                response.getStartDate(),
                response.getEndDate(),
                response.getFinalApprover(),
                response.getFinalApprovalDate()
                );
    }
}
