package com.seveneleven.dto;

import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.Role;
import lombok.Getter;

@Getter
public class GetMemberList {
    private String name;
    private MemberStatus status;
    private Role role;
    private String loginId;

    // 페이지 관련 기본값 설정
    private Integer page = 0;         // 페이지 번호 (기본값 0)
    private Integer size = 10;        // 페이지 크기 (기본값 10)
    private String sort = "id";   // 정렬 기준 필드 (기본값 "id")
    private String direction = "ASC"; // 정렬 방향 (기본값 "ASC")
}
