package com.seveneleven.member.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.member.dto.LoginPost;
import com.seveneleven.member.dto.MemberPatch;
import com.seveneleven.util.security.dto.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;

public interface MemberService {

    LoginPost.Response login(LoginPost.Request request);
    void logout(String token);
    MemberPatch.Response resetPassword(HttpServletRequest reqIp, CustomUserDetails userDetails, MemberPatch.Request request);

    Long getCompanyIdById(Long memberId);
    Member getMember(Long memberId);
}
