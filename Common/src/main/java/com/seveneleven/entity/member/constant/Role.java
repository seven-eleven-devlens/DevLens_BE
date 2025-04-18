package com.seveneleven.entity.member.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("관리자"),
    SUPER("대표 회원"),
    USER("일반 회원");

    private final String description;
}
