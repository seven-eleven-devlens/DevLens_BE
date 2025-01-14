package com.seveneleven.member.service;

import com.seveneleven.config.TokenProvider;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.Token;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.YN;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.dto.LoginPost;
import com.seveneleven.member.dto.MemberPatch;
import com.seveneleven.member.repository.CompanyRepository;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.util.security.TokenRepository;
import com.seveneleven.response.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManagerBuilder authenticationMngrBuilder;

    /**
     * 함수명 : login
     * 사용자 로그인 처리 메서드. 로그인 ID와 비밀번호를 확인하여 인증하고, JWT 토큰을 생성하여 반환합니다.
     *
     * @param request 로그인 요청 정보 (로그인 ID와 비밀번호 포함)
     * @return 생성된 JWT 토큰
     */
    @Transactional
    public LoginPost.Response login(LoginPost.Request request) {

        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPwd(), member.getPassword())) {
            throw new BusinessException(ErrorCode.INCORRECT_PASSWORD);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPwd());

        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationMngrBuilder.getObject().authenticate(authenticationToken);

        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성 후 반환
        String token = tokenProvider.createToken(authentication);

        // JWT 만료 시간 계산
        LocalDateTime expiresAt = tokenProvider.getExpirationFromToken(token);

        // Token 엔티티 생성 및 저장
        Token newToken = Token.create(token, expiresAt);
        tokenRepository.save(newToken);

        Long companyId = member.getCompany().getId();
        String companyName = companyRepository.findNameByIdAndIsActive(companyId, YN.Y);

         return new LoginPost.Response(token, companyId, companyName, "", "");
    }

    /**
     * 함수명 : logout
     * 로그아웃 처리 메서드. 주어진 토큰을 DB에서 찾아 상태를 BLACKLISTED로 변경하여 로그아웃 처리합니다.
     *
     * @param token 로그아웃할 사용자 토큰. "Bearer " 접두어가 포함될 수 있습니다.
     */
    @Transactional
    public void logout(String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 토큰 객체 조회
        Token existingToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new BusinessException(ErrorCode.EXPIRED_TOKEN));

        // 상태를 BLACKLISTED로 변경
        existingToken.setStatus();
        tokenRepository.save(existingToken);
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


    public Long getCompanyIdById(Long memberId) {
        return memberRepository.findCompanyIdByIdAndStatus(memberId, MemberStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));
    }
}
