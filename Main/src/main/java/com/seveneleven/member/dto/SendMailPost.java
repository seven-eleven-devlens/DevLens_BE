package com.seveneleven.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class SendMailPost {
    @Getter
    public static class Request{
        private String email;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private String key;
    }
}
