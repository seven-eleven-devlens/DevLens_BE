package com.seveneleven.member.service;

import com.seveneleven.member.dto.LoginPost;
import com.seveneleven.member.dto.MemberPatch;

public interface MemberService {

    LoginPost.Response login(LoginPost.Request request);
    void logout(String token);
    MemberPatch.Response resetPassword(String LoginId, MemberPatch.Request request);
}
