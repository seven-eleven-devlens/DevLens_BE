package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.admin.common.EntityConverter;
import com.seveneleven.devlens.domain.admin.dto.PostProject;
import com.seveneleven.devlens.domain.admin.exception.CompanyNotFoundException;
import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.project.entity.Project;
import com.seveneleven.devlens.domain.project.entity.ProjectType;
import com.seveneleven.devlens.global.entity.YesNo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostProjectRequestConverter implements EntityConverter<PostProject.Request, Project> {
    private final CompanyRepository companyRepository;
    private final AdminMemberRepository adminMemberRepository;
    private final AdminProjectTypeRepository projectTypeRepository;

    @Override
    public PostProject.Request toDTO(Project project) {
        //쓰이지 않는 메서드
        return null;
    }

    @Override
    public Project toEntity(PostProject.Request request) {
        Company customer = companyRepository.findById(request.getCustomerId()).orElseThrow(CompanyNotFoundException::new);
        Company developer = companyRepository.findById(request.getDeveloperId()).orElseThrow(CompanyNotFoundException::new);
        Member bnsManager = adminMemberRepository.findById(request.getBnsManager()).orElseThrow(() -> new EntityNotFoundException("멤버를 찾을 수 없습니다."));
        ProjectType projectTypeId = projectTypeRepository.findById(request.getProjectTypeId()).orElseThrow(() -> new EntityNotFoundException("프로젝트 타입을 찾을 수 없습니다."));
        return new Project(
                request.getProjectName(),
                customer,
                developer,
                request.getProjectDescription(),
                projectTypeId,
                Project.projectStatusCode.PREPARED,
                bnsManager,
                request.getContractNumber(),
                request.getPlannedStartDate(),
                request.getPlannedEndDate()
        );
    }
}
