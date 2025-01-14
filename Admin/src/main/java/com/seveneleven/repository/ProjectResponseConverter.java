package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.GetProject;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectType;
import com.seveneleven.exception.CompanyNotFoundException;
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
        return GetProject.Response.of(project);
    }

    @Override
    public Project toEntity(
            GetProject.Response response
    ) {
        Company customer = companyRepository.findById(response.getCustomerId()).orElseThrow(CompanyNotFoundException::new);
        Company developer = companyRepository.findById(response.getDeveloperId()).orElseThrow(CompanyNotFoundException::new);
        Member bnsManager = adminMemberRepository.findById(response.getBnsManager()).orElseThrow(EntityNotFoundException::new);
        ProjectType projectType = projectTypeRepository.findById(response.getProjectTypeId()).orElseThrow(EntityNotFoundException::new);
        return Project.getProject(
                response.getId(),
                response.getProjectName(),
                customer,
                developer,
                response.getProjectDescription(),
                projectType,
                response.getProjectStatusCode(),
                bnsManager,
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
