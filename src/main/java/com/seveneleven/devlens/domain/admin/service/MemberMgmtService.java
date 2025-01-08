package com.seveneleven.devlens.domain.admin.service;


import com.seveneleven.devlens.domain.admin.dto.MemberDto;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class MemberMgmtService {

    private final MemberRepository memberRepository;

    public List<MemberDto> getAllMembers() {

        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(MemberDto::fromEntity)
                .collect(Collectors.toList());
    }
}
