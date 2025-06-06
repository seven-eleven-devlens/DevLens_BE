package com.seveneleven.entity.member.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    WITHDRAW("탈퇴"),
    SUSPENDED("정지");

    private final String description;
}
