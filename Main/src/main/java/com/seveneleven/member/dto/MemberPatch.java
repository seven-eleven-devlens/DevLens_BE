package com.seveneleven.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MemberPatch {

    @Getter
    @Setter
    public static class Request{
        private String password;
        private String newPassword;

        @Override
        public String toString() {
            return "Request{" +
                    "newPassword='" + newPassword + '\'' +
                    '}';
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private String loginId;

        @Override
        public String toString() {
            return "Response{" +
                    "loginId='" + loginId + '\'' +
                    '}';
        }
    }
}
