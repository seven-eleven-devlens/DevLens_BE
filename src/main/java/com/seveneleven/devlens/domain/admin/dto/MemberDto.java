package com.seveneleven.devlens.domain.admin.dto;

import com.seveneleven.devlens.domain.member.constant.MemberStatus;
import com.seveneleven.devlens.domain.member.constant.Role;
import com.seveneleven.devlens.domain.member.constant.YN;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDto {

    // **Member로 변환하는 메서드**
    private static final ModelMapper modelMapper = new ModelMapper();

    public com.seveneleven.devlens.domain.member.entity.Member toEntity() {
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(this, com.seveneleven.devlens.domain.member.entity.Member.class);
    }

    public static MemberDto.Response fromEntity(com.seveneleven.devlens.domain.member.entity.Member member) {
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
        private YN profileImageExists;
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

    @Getter
    @Setter
    public static class PatchRequest{
        private String name;
        private Long companyId;
        private Role role;
        private String phoneNumber;
        private Long departmentId;
        private Long positionId;
        private YN profileImageExists;
    }

}

