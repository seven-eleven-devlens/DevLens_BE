package com.seveneleven.devlens.domain.admin.dto;

import com.seveneleven.devlens.domain.member.constant.MemberStatus;
import com.seveneleven.devlens.domain.member.constant.Role;
import com.seveneleven.devlens.domain.member.entity.Member;
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

    public static MemberDto.Response fromEntity(Member member) {
        return modelMapper.map(member, MemberDto.Response.class);
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
        private Long departmentId;
        private Long positionId;

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
        private Long departmentId;
        private Long positionId;
    }


}

