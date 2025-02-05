package com.seveneleven.member.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.Role;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import java.time.LocalDate;

public class MemberDto {

    // **Member로 변환하는 메서드**
    private static final ModelMapper modelMapper = new ModelMapper();

    public Member toEntity() {
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(this, Member.class);
    }

    public static Response fromEntity(Member member) {
        return modelMapper.map(member, Response.class);
    }

    @Getter
    @Setter
    public static class Response{
        private Long id;
        private String loginId;
        private String name;
        private String email;
        private Role role;
        private MemberStatus status;
        private String phoneNumber;
        private LocalDate birthDate;
        private Long companyId;
        private String company;
        private String department;
        private String position;

    }

    @Getter
    @Setter
    public static class Request{
        private Long companyId;
        private String loginId;
        private String password;
        private String name;
        private String email;
        private Role role;
        private String phoneNumber;
        private LocalDate birthDate;
        private String department;
        private String position;
    }


}

