package com.seveneleven.service;

import com.seveneleven.dto.MemberDto;
import com.seveneleven.dto.MemberUpdate;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberMgmtService {

    Page<MemberDto.Response> getFilteredMembers(String name, MemberStatus status, Role role, String loginId, Pageable pageable);

    MemberDto.Response getMemberDetail(String loginId);

    MemberDto.Response createMember(MemberDto.Request memberDto);


    List<MemberDto.Response> createMembers(List<MemberDto.Request> memberDtos);


    MemberDto.Response updateMember(String loginId, MemberUpdate.PatchRequest memberDto) throws InterruptedException;

    void deleteMember(String loginId);

    MemberUpdate.PatchResponse resetPassword(String loginId);
}
