package com.seveneleven.devlens.domain.member.service;

import com.seveneleven.devlens.domain.member.entity.Member;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.rmi.NoSuchObjectException;

@Component
public class MyUserDetailsService implements UserDetailsService {
    private final MemberService memberService;

    public MyUserDetailsService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String insertedUserId){
        Member member = memberService.findOne(insertedUserId);

        if (member == null) {
            throw new UsernameNotFoundException("User with ID '" + insertedUserId + "' not found.");
        }

        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
