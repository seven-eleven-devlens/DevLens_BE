package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.admin.common.EntityConverter;
import com.seveneleven.devlens.domain.admin.dto.ProjectDto;
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
public class ProjectRequestConverter implements EntityConverter<ProjectDto.ProjectRequest, Project> {
    private final CompanyRepository companyRepository;
    private final AdminMemberRepository adminMemberRepository;
    private final ProjectTypeRepository projectTypeRepository;
    @Override
    public ProjectDto.ProjectRequest toDTO(
            Project project
    ) {
        return new ProjectDto.ProjectRequest(
                project.getId(),
                project.getProjectName(),
                project.getCustomer().getId(),
                project.getDeveloper().getId(),
                project.getProjectDescription(),
                project.getProjectTypeId().getId(),
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
            ProjectDto.ProjectRequest request
    ) {
        Company customer = companyRepository.findById(request.getCustomerId()).orElseThrow(CompanyNotFoundException::new);
        Company developer = companyRepository.findById(request.getDeveloperId()).orElseThrow(CompanyNotFoundException::new);
        Member bnsManager = adminMemberRepository.findById(request.getBnsManager()).orElseThrow(EntityNotFoundException::new);
        ProjectType projectTypeId = projectTypeRepository.findById(request.getProjectTypeId()).orElseThrow(EntityNotFoundException::new);
        return new Project(
                request.getId(),
                request.getProjectName(),
                customer,
                developer,
                request.getProjectDescription(),
                projectTypeId,
                request.getProjectStatusCode(),
                bnsManager,
                request.getHasImage(),
                request.getContractNumber(),
                request.getPlannedStartDate(),
                request.getPlannedEndDate(),
                request.getStartDate(),
                request.getEndDate(),
                request.getFinalApprover(),
                request.getFinalApprovalDate()
        );
    }
}
