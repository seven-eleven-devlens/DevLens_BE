package com.seveneleven.devlens.domain.admin.repository;

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
public class ProjectRequestConverter implements EntityConverter<GetProject.Request, Project> {
    private final CompanyRepository companyRepository;
    private final AdminMemberRepository adminMemberRepository;
    private final AdminProjectTypeRepository projectTypeRepository;

    @Override
    public GetProject.Request toDTO(
            Project project
    ) {
        return new GetProject.Request(
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
            GetProject.Request request
    ) {
        Company customer = companyRepository.findById(request.getCustomerId()).orElseThrow(CompanyNotFoundException::new);
        Company developer = companyRepository.findById(request.getDeveloperId()).orElseThrow(CompanyNotFoundException::new);
        Member bnsManager = adminMemberRepository.findById(request.getBnsManager()).orElseThrow(() -> new EntityNotFoundException("멤버를 찾을 수 없습니다."));
        ProjectType projectTypeId = projectTypeRepository.findById(request.getProjectTypeId()).orElseThrow(() -> new EntityNotFoundException("프로젝트 타입을 찾을 수 없습니다."));
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
