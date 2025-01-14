package com.seveneleven.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

/*
 * security, jwt 테스트 확인을 위해 임시로 만든 dto
 *
 * */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String loginId;
    private String username;
    private String email;
    private String password;
    private Role role;

    // **Member로 변환하는 메서드**
    private static final ModelMapper modelMapper = new ModelMapper();

    public static MemberDto fromEntity(Member member) {
        return modelMapper.map(member, MemberDto.class);
    }

}
