package com.seveneleven.member.service;

import com.seveneleven.config.CustomUserDetailsService;
import com.seveneleven.entity.member.Member;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.dto.CustomUserDetails;
import com.seveneleven.member.dto.MemberDto;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.response.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId){
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new CustomUserDetails(MemberDto.fromEntity(member));
    }
}
