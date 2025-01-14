package com.seveneleven.member.service;

import com.seveneleven.member.dto.LoginRequest;
import com.seveneleven.member.dto.MemberPatch;

public interface MemberService {

    String login(LoginRequest request);

    MemberPatch.Response resetPassword(MemberPatch.Request request);
}
