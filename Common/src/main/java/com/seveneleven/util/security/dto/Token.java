package com.seveneleven.util.security.dto;

import lombok.*;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
public class Token {
    private String accessToken;
    private String refreshToken;
}