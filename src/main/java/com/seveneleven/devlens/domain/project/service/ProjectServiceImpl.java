package com.seveneleven.devlens.domain.project.service;

import com.seveneleven.devlens.domain.member.constant.MemberStatus;
import com.seveneleven.devlens.domain.member.repository.MemberRepository;
import com.seveneleven.devlens.domain.member.service.MemberService;
import com.seveneleven.devlens.domain.project.dto.GetProjectDetail;
import com.seveneleven.devlens.domain.project.dto.GetProjectList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectReader projectReader;
    private final MemberService memberService;

    @Override
    public GetProjectList.Response getProjectList(Long memberId) {
        // TODO - securityUtils 사용 확인
        // TODO - MemberRepository -> Reader로 변경 필요

        return projectReader.getProjectList(memberId, memberService.getCompanyIdById(memberId));
    }

    @Override
    public GetProjectDetail.Response getProjectDetail(Long projectId) {
        return projectReader.getProjectDetail(projectId);
    }
}
