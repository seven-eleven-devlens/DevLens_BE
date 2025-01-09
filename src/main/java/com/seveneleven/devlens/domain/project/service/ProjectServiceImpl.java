package com.seveneleven.devlens.domain.project.service;

import com.seveneleven.devlens.domain.member.constant.MemberStatus;
import com.seveneleven.devlens.domain.member.repository.MemberRepository;
import com.seveneleven.devlens.domain.project.dto.GetProjectDetail;
import com.seveneleven.devlens.domain.project.dto.GetProjectList;
import com.seveneleven.devlens.global.util.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectReader projectReader;
    // 임시 등록
    private final MemberRepository memberRepository;
    private final SecurityUtils securityUtils;

    @Override
    public GetProjectList.Response getProjectList() {
        // TODO - securityUtils 사용 확인
        // TODO - MemberRepository -> Reader로 변경 필요

        List<Long> memberIdAndCompanyId = memberRepository
                .findByMemberIdAndStatusCode(
                        securityUtils.getUserIdInSecurityContext(),
                        MemberStatus.ACTIVE
                );

        return projectReader.getProjectList(memberIdAndCompanyId.get(0), memberIdAndCompanyId.get(1));
    }

    @Override
    public GetProjectDetail.Response getProjectDetail(Long projectId) {
        return projectReader.getProjectDetail(projectId);
    }
}
