package com.seveneleven.service;

import com.seveneleven.entity.member.Member;

public interface AdminMemberReader {
    Member getMember(Long id);
}

