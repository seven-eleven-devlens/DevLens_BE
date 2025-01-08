package com.seveneleven.devlens.domain.admin.dto;

import com.seveneleven.devlens.domain.member.constant.MemberStatus;
import com.seveneleven.devlens.domain.member.constant.Role;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDto {


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


    // **Member로 변환하는 메서드**
//    private static final ModelMapper modelMapper = new ModelMapper();
//
//    public Member toEntity() {
//        modelMapper.getConfiguration()
//                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
//                .setFieldMatchingEnabled(true);
//        return modelMapper.map(this, Member.class);
//    }
}

