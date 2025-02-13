package com.seveneleven.member.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.MemberPasswordResetHistory;
import com.seveneleven.entity.member.MemberProfileHistory;

public interface MemberStore {
    Member storeMember(Member member);
    void storePasswordHistory(MemberPasswordResetHistory passwordHistory);
    void storeProfileHistory(MemberProfileHistory profileHistory);
}
