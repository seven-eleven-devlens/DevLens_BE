package com.seveneleven.member.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberReaderImpl implements MemberReader{
    private final MemberRepository memberRepository;

    @Override
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public Member getMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public Member getActiveMemberByMemberId(Long memberId) {
        return memberRepository.findByIdAndStatus(memberId, MemberStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public Member getActiveMemberByLoginId(String loginId) {
        return memberRepository.findByLoginIdAndStatus(loginId, MemberStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
