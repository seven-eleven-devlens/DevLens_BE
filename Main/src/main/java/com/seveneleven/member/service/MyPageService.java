package com.seveneleven.member.service;

import com.seveneleven.member.dto.MyPageGetMember;

public interface MyPageService {

    public MyPageGetMember getMember(String loginId);
}
