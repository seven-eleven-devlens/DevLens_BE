package com.seveneleven.member.dto;

import lombok.Getter;

@Getter
public class CheckMailPostRequest {
    private String inputKey;
    private String key;

    @Override
    public String toString() {
        return "CheckMailPostRequest{" +
                "inputKey='" + inputKey + '\'' +
                '}';
    }
}
