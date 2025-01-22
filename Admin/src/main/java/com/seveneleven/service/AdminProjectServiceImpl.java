package com.seveneleven.service;

import com.seveneleven.dto.GetProject;
import com.seveneleven.dto.PaginatedResponse;
import com.seveneleven.dto.PostProject;
import com.seveneleven.dto.PutProject;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectType;
import com.seveneleven.exception.ProjectNotFoundException;
import com.seveneleven.repository.AdminMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.seveneleven.common.PageSize.DEFAULT_PAGE_SIZE;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminProjectServiceImpl implements AdminProjectService {
    private final AdminMemberRepository adminMemberRepository;
    private final AdminProjectReader adminProjectReader;
    private final AdminProjectStore adminProjectStore;
    private final AdminCompanyReader adminCompanyReader;
    private final AdminProjectTypeReader adminProjectTypeReader;

    @Override
    public PostProject.Response createProject(PostProject.Request request) {
        //중복 확인
        adminProjectReader.checkProjectExists(request.getProjectName());

        Company customer = adminCompanyReader.getCompany(request.getCustomerId());
        Company developer = adminCompanyReader.getCompany(request.getDeveloperId());
        Member bnsManager = adminMemberRepository.findById(request.getBnsManagerId()).orElseThrow(() -> new EntityNotFoundException("bns 담당자를 찾을 수 없습니다."));
        ProjectType projectType = adminProjectTypeReader.getProjectType(request.getProjectTypeId());

        Project project = adminProjectStore.store(request.toEntity(customer, developer, projectType, bnsManager));
        return PostProject.Response.of(project);
    }

    @Transactional(readOnly = true)
    @Override
    public GetProject.Response getProject(Long id) {
        return GetProject.Response.of(adminProjectReader.getProject(id));
    }

    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<GetProject.Response> getListOfProject(Integer page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE.getPageSize(), Sort.by("id").descending());
        Page<Project> projects = adminProjectReader.findAll(pageable);
        if (projects.getContent().isEmpty()) throw new ProjectNotFoundException();

        return PaginatedResponse.createPaginatedResponse(projects.map(GetProject.Response::of));
    }

    @Transactional
    @Override
    public PutProject.Response updateProject(Long id, PutProject.Request request) {
        Project project = adminProjectReader.getProject(id);
        //현재 프로젝트 명과 다르면 중복 체크
        if (!project.getProjectName().equals(request.getProjectName())) {
            adminProjectReader.checkProjectExists(request.getProjectName());
        }

        Company customer = adminCompanyReader.getCompany(request.getCustomerId());
        Company developer = adminCompanyReader.getCompany(request.getDeveloperId());
        Member bnsManager = adminMemberRepository.findById(request.getBnsManagerId()).orElseThrow(() -> new EntityNotFoundException("bns 담당자를 찾을 수 없습니다."));
        ProjectType projectType = adminProjectTypeReader.getProjectType(request.getProjectTypeId());

        Project updatedProject = request.updateProject(project, customer, developer, projectType, bnsManager);
        return PutProject.Response.of(adminProjectStore.store(updatedProject));
    }

    @Transactional
    @Override
    public void deleteProject(Long id) {
        adminProjectStore.delete(id);
    }
}