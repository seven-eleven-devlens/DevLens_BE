package com.seveneleven.service;

import com.seveneleven.dto.GetMemberList;
import com.seveneleven.dto.LoginPost;
import com.seveneleven.dto.MemberDto;
import com.seveneleven.dto.MemberUpdate;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminMemberService {
    LoginPost.Response login(LoginPost.Request request);

    GetMemberList.Response getFilteredMembers(GetMemberList.Request memberList);

    MemberDto.Response getMemberDetail(Long memberId);

    MemberDto.Response createMember(MemberDto.Request memberDto);

    List<MemberDto.Response> createMembers(List<MemberDto.Request> memberDtos);

    MemberDto.Response updateMember(Long memberId, MemberUpdate.PatchRequest memberDto);

    void deleteMember(Long memberId);

    MemberUpdate.PatchResponse resetPassword(HttpServletRequest reqIp, Long memberId);
}