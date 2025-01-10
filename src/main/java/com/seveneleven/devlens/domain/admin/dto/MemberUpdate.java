package com.seveneleven.devlens.domain.admin.dto;

import com.seveneleven.devlens.domain.member.constant.Role;
import com.seveneleven.devlens.domain.member.entity.Member;
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

    public static MemberUpdate.PatchResponse fromEntity(Member member) {
        return modelMapper.map(member, MemberUpdate.PatchResponse.class);
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
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PatchResponse{
        private String loginId;
        private String password;
    }
}