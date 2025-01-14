package com.seveneleven.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MemberPatch {

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
