package com.seveneleven.member.dto;

import lombok.Getter;

@Getter
public class CheckMailPostRequest {
    private String email;
    private String inputKey;
    private String key;
}
