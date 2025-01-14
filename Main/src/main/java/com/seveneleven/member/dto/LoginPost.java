package com.seveneleven.member.dto;

import lombok.*;

/*
 * security, jwt 테스트 확인을 위해 임시로 만든 dto
 *
 * */
public class LoginPost {

    @Getter
    public static class Request{
        private String loginId;
        private String pwd;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private String token;
        private Long companyId;
        private String companyName;
        private String department;
        private String position;
    }
}
