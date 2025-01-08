package com.seveneleven.devlens.domain.member.service;

import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.member.repository.MemberRepository;
import com.seveneleven.devlens.global.util.security.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;


    public Optional<Member> findOne(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

//    public Long join(String userid, String pw) {
//        Optional<Company> companyOptional = companyRepository.findById(1L);
//
//        Company company = companyOptional.orElseThrow(() ->
//                new IllegalStateException("Company with ID 1L not found")
//        );
//
//        Member member = Member.createMember(userid, pw, company, Role.USER, "박철수", "admin@admin.com", LocalDate.now(),"010-111-1111",1L,1L,passwordEncoder);
//        repository.save(member);
//
//        return member.getId();
//    }

    // 유저,권한 정보를 가져오는 메소드
    @Transactional
    public Optional<Member> getUserWithAuthorities(String memberId) {
        return memberRepository.findOneWithAuthoritiesByLoginId(memberId);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional
    public Optional<Member> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(memberRepository::findOneWithAuthoritiesByLoginId);
    }
}
