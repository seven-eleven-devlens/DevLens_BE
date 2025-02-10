package com.seveneleven.member.dto;

import com.seveneleven.entity.member.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String loginId;
    private String name;
    private String email;
    private Role role;
    private String profileUrl;
    private Long companyId;
    private String companyName;
    private String department;
    private String position;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "loginId='" + loginId + '\'' +
                '}';
    }
}
