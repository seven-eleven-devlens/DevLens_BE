package com.seveneleven.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

@Getter
@Setter
public class MemberUpdate {

    // **Member로 변환하는 메서드**
    private static final ModelMapper modelMapper = new ModelMapper();

    public Member toEntity() {
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(this, Member.class);
    }

    public static PatchResponse fromEntity(Member member) {
        return modelMapper.map(member, PatchResponse.class);
    }

    @Getter
    @Setter
    public static class PatchRequest{
        private String name;
        private Long companyId;
        private Role role;
        private String phoneNumber;
        private String department;
        private String position;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PatchResponse{
        private String loginId;
        private String password;
    }
}