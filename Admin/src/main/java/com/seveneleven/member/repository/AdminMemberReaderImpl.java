package com.seveneleven.member.repository;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.service.AdminMemberReader;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminMemberReaderImpl implements AdminMemberReader {
    private final AdminMemberRepository adminMemberRepository;

    @Override
    public Member getMember(Long id) {
        return adminMemberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public List<Member> getCompanyMember(Long companyId) {
        return adminMemberRepository
                .findMembersByCompanyIdAndStatus(companyId, MemberStatus.ACTIVE);
    }

    @Override
    public Member getMemberById(Long memberId) {
        return adminMemberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public Member getActiveMemberByLoginId(String loginId) {
        return adminMemberRepository.findByLoginIdAndStatus(loginId, MemberStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public Page<Member> getMemberAll(Specification<Member> spec, Pageable pageable) {
        return adminMemberRepository.findAll(pageable);
    }

    @Override
    public Boolean getExistsByLoginId(String loginId) {
        return adminMemberRepository.existsByLoginId(loginId);
    }

    @Override
    public Boolean getExistsByEmail(String email) {
        return adminMemberRepository.existsByEmail(email);
    }
}
