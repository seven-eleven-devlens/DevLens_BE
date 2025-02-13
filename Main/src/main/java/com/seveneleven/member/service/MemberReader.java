package com.seveneleven.member.service;

import com.seveneleven.entity.member.Member;

public interface MemberReader {
    Member getMemberById(Long memberId);
    Member getMemberByLoginId(String loginId);
    Member getActiveMemberByEmail(String email);
    Member getActiveMemberByMemberId(Long memberId);
    Member getActiveMemberByLoginId(String loginId);
}
