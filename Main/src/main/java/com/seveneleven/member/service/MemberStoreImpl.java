package com.seveneleven.member.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.MemberPasswordResetHistory;
import com.seveneleven.entity.member.MemberProfileHistory;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.member.repository.PasswordHistoryRepository;
import com.seveneleven.member.repository.ProfileHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore{

    private final MemberRepository memberRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;
    private final ProfileHistoryRepository profileHistoryRepository;

    @Override
    public Member storeMember(Member member) {
        return memberRepository.save(member);
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
