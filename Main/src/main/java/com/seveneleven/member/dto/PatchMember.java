package com.seveneleven.member.dto;

import com.seveneleven.entity.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class PatchMember {
    // **Member로 변환하는 메서드**
    private static final ModelMapper modelMapper = new ModelMapper();

    public static PatchMember.Response fromEntity(Member member) {
        return modelMapper.map(member, PatchMember.Response.class);
    }

    @Getter
    @Setter
    public static class Request{
        private String email;
        private String phoneNumber;
        private Long companyId;
        private String department;
        private String position;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Response{
        private String name;
        private String email;
        private String phoneNumber;
        private Long companyId;
        private String company;
        private String department;
        private String position;
    }
}
