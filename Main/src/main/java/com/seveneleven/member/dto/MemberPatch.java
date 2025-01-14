package com.seveneleven.member.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

public class MemberPatch {

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
    public static class Request{
        private String loginId;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private String loginId;
    }
}
