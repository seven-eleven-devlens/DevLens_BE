package com.seveneleven.member.service;


import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.MemberPasswordResetHistory;
import com.seveneleven.entity.member.MemberProfileHistory;

import java.util.List;

public interface AdminMemberStore {
    Member storeMember(Member member);
    List<Member> storeMemberAll(List<Member> members);
    void storePasswordHistory(MemberPasswordResetHistory passwordHistory);
    void storeProfileHistory(MemberProfileHistory profileHistory);
}

