package com.seveneleven.member.service;

import com.seveneleven.member.dto.LoginPost;
import com.seveneleven.member.dto.MemberPatch;

public interface MemberService {

    LoginPost.Response login(LoginPost.Request request);
    void logout(String token);
    MemberPatch.Response resetPassword(MemberPatch.Request request);
}
