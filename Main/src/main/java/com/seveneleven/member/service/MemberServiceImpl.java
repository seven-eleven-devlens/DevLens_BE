package com.seveneleven.member.service;

import com.seveneleven.config.TokenProvider;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.dto.LoginRequest;
import com.seveneleven.member.dto.MemberPatch;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.response.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationMngrBuilder;

    @Transactional
    public String login(LoginRequest request) {

        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPwd(), member.getPassword())) {
            throw new BusinessException(ErrorCode.INCORRECT_PASSWORD);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPwd());

        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationMngrBuilder.getObject().authenticate(authenticationToken);

        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성 후 반환
        return tokenProvider.createToken(authentication);
    }


    public Optional<Member> findOne(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

//
//
//    // 유저,권한 정보를 가져오는 메소드
//    @Transactional
//    public Optional<Member> getUserWithAuthorities(String memberId) {
//        return memberRepository.findOneWithAuthoritiesByLoginId(memberId);
//    }
//
//    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
//    @Transactional
//    public Optional<Member> getMyUserWithAuthorities() {
//        return SecurityUtil.getCurrentUsername()
//                .flatMap(memberRepository::findOneWithAuthoritiesByLoginId);
//    }
//
    public Long getCompanyIdById(Long memberId) {
        return memberRepository.findCompanyIdByIdAndStatus(memberId, MemberStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));
    }

    /**
     * 함수명 : resetPassword
     * 회원 비밀번호를 초기화합니다.
     *
     * @param request 비밀번호를 재설정할 회원의 정보.
     * @return 비밀번호 재설정한 회원 LoginId
     */
    @Transactional
    public MemberPatch.Response resetPassword(MemberPatch.Request request) {
        // 1. 회원 조회

        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 2. 비밀번호 암호화 후 저장
        member.resetPassword(passwordEncoder.encode(request.getPassword()));

        // 3. 생성된 비밀번호 반환
        return new MemberPatch.Response(request.getLoginId());
    }
}
