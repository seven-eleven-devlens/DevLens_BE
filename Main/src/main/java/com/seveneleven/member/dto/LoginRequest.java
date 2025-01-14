package com.seveneleven.member.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * security, jwt 테스트 확인을 위해 임시로 만든 dto
 *
 * */
@Getter
@Setter
public class LoginRequest {

    private String loginId;
    private String pwd;



}
