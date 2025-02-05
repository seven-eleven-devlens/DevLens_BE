package com.seveneleven.member.service;

import com.seveneleven.member.dto.GetMemberList;
import com.seveneleven.member.dto.LoginPost;
import com.seveneleven.member.dto.MemberDto;
import com.seveneleven.member.dto.MemberUpdate;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AdminMemberService {
    LoginPost.Response login(LoginPost.Request request);

    GetMemberList.Response getFilteredMembers(GetMemberList.Request memberList);

    MemberDto.Response getMemberDetail(Long memberId);

    MemberDto.Response createMember(MemberDto.Request memberDto);

    List<MemberDto.Response> createMembers(List<MemberDto.Request> memberDtos);

    MemberDto.Response updateMember(Long memberId, MemberUpdate.PatchRequest memberDto);

    void deleteMember(Long memberId);

    MemberUpdate.PatchResponse resetPassword(HttpServletRequest request, Long memberId);
}