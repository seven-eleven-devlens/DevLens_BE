package com.seveneleven.dto;

import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMemberList {
    private String name;
    private MemberStatus status;
    private Role role;
    private String loginId;

    private int page = 0;
    private int size = 10;
    private String sort = "id";
    private String direction = "asc";
}
