package com.seveneleven.member.service;

import com.seveneleven.member.dto.MyPageGetMember;
import com.seveneleven.member.dto.PatchMember;

public interface MyPageService {

    MyPageGetMember getMember(String loginId);

    PatchMember.Response updateMember(String loginId, PatchMember.Request requestDto);

    void deleteMember(String loginId);
}
