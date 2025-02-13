package com.seveneleven.member.repository;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.MemberPasswordResetHistory;
import com.seveneleven.entity.member.MemberProfileHistory;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.service.AdminMemberReader;
import com.seveneleven.member.service.AdminMemberStore;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminMemberStoreImpl implements AdminMemberStore {
    private final AdminMemberRepository adminMemberRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;
    private final ProfileHistoryRepository profileHistoryRepository;

    @Override
    public Member storeMember(Member member) {
        return adminMemberRepository.save(member);
    }

    @Override
    public List<Member> storeMemberAll(List<Member> members) {
        return adminMemberRepository.saveAll(members);
    }

    @Override
    public void storePasswordHistory(MemberPasswordResetHistory passwordHistory) {
        passwordHistoryRepository.save(passwordHistory);
    }

    @Override
    public void storeProfileHistory(MemberProfileHistory profileHistory) {
        profileHistoryRepository.save(profileHistory);
    }

}
