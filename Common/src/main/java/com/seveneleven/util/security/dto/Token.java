package com.seveneleven.util.security.dto;

import lombok.*;

@Data
@ToString
@Getter
@Setter
@AllArgsConstructor
public class Token {
    private String accessToken;
    private String refreshToken;
}


