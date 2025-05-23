package com.seveneleven.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * 로그인 API DTO
 *
 * */
public class LoginPost {

    @Getter
    public static class Request{
        private String loginId;
        private String password;

        @Override
        public String toString() {
            return "Request{" +
                    "loginId='" + loginId + '\'' +
                    '}';
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private String accessToken;
        private Long expiredAccess;
        private String refreshToken;
        private Long expiredRefresh;
        private LoginResponse companyInfo;

        @Override
        public String toString() {
            return "Response{" +
                    "accessToken='" + accessToken + '\'' +
                    '}';
        }
    }
}
