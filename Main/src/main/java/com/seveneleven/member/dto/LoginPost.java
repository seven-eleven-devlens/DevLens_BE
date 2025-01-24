package com.seveneleven.member.dto;

import com.seveneleven.util.security.dto.TokenResponse;
import lombok.*;

/*
 * 로그인 API DTO
 *
 * */
public class LoginPost {

    @Getter
    public static class Request{
        private String loginId;
        private String password;
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
    }
}
