package com.seveneleven.member.repository;

import com.seveneleven.entity.member.Member;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.service.AdminMemberReader;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAdminMemberReaderImpl implements AdminMemberReader {
    private final AdminMemberRepository adminMemberRepository;
    @Override
    public Member getMember(Long id) {
        return adminMemberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
