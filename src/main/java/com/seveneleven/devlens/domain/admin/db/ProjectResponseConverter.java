package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.admin.common.EntityConverter;
import com.seveneleven.devlens.domain.admin.dto.GetProject;
import com.seveneleven.devlens.domain.admin.exception.CompanyNotFoundException;
import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.project.entity.Project;
import com.seveneleven.devlens.domain.project.entity.ProjectType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProjectResponseConverter implements EntityConverter<GetProject.Response, Project> {
    private final CompanyRepository companyRepository;
    private final AdminMemberRepository adminMemberRepository;
    private final AdminProjectTypeRepository projectTypeRepository;

    @Override
    public GetProject.Response toDTO(
            Project project
    ) {
        return new GetProject.Response(
                project.getId(),
                project.getProjectName(),
                project.getCustomer().getId(),
                project.getDeveloper().getId(),
                project.getProjectDescription(),
                project.getProjectType().getId(),
                project.getProjectStatusCode(),
                project.getBnsManager().getId(),
                project.getHasImage(),
                project.getContractNumber(),
                project.getPlannedStartDate(),
                project.getPlannedEndDate(),
                project.getStartDate(),
                project.getEndDate(),
                project.getFinalApprover(),
                project.getFinalApprovalDate()
        );
    }

    @Override
    public Project toEntity(
            GetProject.Response response
    ) {
        Company customer = companyRepository.findById(response.getCustomerId()).orElseThrow(CompanyNotFoundException::new);
        Company developer = companyRepository.findById(response.getDeveloperId()).orElseThrow(CompanyNotFoundException::new);
        Member bnsManager = adminMemberRepository.findById(response.getBnsManager()).orElseThrow(EntityNotFoundException::new);
        ProjectType projectType = projectTypeRepository.findById(response.getProjectTypeId()).orElseThrow(EntityNotFoundException::new);
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
