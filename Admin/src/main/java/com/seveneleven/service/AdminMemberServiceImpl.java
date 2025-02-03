package com.seveneleven.service;

import com.seveneleven.config.TokenProvider;
import com.seveneleven.dto.*;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.Role;
import com.seveneleven.entity.member.constant.YN;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.MemberValidator;
import com.seveneleven.repository.AdminMemberRepository;
import com.seveneleven.repository.CompanyRepository;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.security.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 회원 관리 서비스 클래스.
 *
 * 회원의 생성, 조회, 수정, 삭제 및 기타 관련 비즈니스 로직을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {


    private final AuthenticationManagerBuilder authenticationMngrBuilder;
    private final AdminMemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    /**
     * 함수명 : login
     * 사용자 로그인 처리 메서드. 로그인 ID와 비밀번호를 확인하여 인증하고, JWT 토큰을 생성하여 반환합니다.
     *
     * @param request 로그인 요청 정보 (로그인 ID와 비밀번호 포함)
     * @return 생성된 JWT 토큰
     */
    @jakarta.transaction.Transactional
    public LoginPost.Response login(LoginPost.Request request) {

        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BusinessException(ErrorCode.INCORRECT_PASSWORD);
        }

        // Access Token, Refresh Token 생성
        TokenResponse tokens = getToken(request.getLoginId(), request.getPassword());

        LoginResponse company = new LoginResponse(member.getLoginId(),member.getName(),member.getEmail(),
                member.getRole(),"", 0L, "", member.getDepartment(), member.getPosition());

        return new LoginPost.Response(tokens.accessToken(),
                tokenProvider.getAccessTokenExpireTime(),
                tokens.refreshToken(),
                tokenProvider.getRefreshTokenExpireTime(),
                company);
    }

    /**
     * 함수명 : getFilteredMembers
     * 필터 조건에 따라 회원 목록을 조회합니다.
     *
     * @param name     회원 이름 필터 (옵션).
     * @param status   회원 상태 필터 (옵션).
     * @param role     회원 역할 필터 (옵션).
     * @param loginId  로그인 ID 필터 (옵션).
     * @param pageable 페이징 정보.
     * @return 필터 조건에 맞는 회원 목록.
     */
    @Transactional(readOnly = true)
    public Page<MemberDto.Response> getFilteredMembers(
            String name, MemberStatus status, Role role, String loginId, Pageable pageable) {

        Specification<Member> spec = Specification
                .where(MemberSpecification.hasName(name))
                .and(MemberSpecification.hasStatus(status))
                .and(MemberSpecification.hasRole(role))
                .and(MemberSpecification.hasLoginId(loginId));

        Page<Member> members = memberRepository.findAll(spec, pageable);

        // 엔티티 -> DTO 변환
        return members.map(MemberDto::fromEntity);
    }

    /**
     * 함수명 : getMemberDetail
     * 회원 상세 정보를 조회합니다.
     *
     * @param memberId 회원 ID.
     * @return 회원 상세 정보 DTO.
     */
    @Transactional(readOnly = true)
    public MemberDto.Response getMemberDetail(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return MemberDto.fromEntity(member);
    }

    /**
     * 함수명 : createMember
     * 새로운 회원을 생성합니다.
     *
     * @param memberDto 생성할 회원의 요청 데이터.
     * @return 생성된 회원의 응답 DTO.
     */
    @Transactional
    public MemberDto.Response createMember(MemberDto.Request memberDto) {
        // 1. 회사 조회
        Company company = companyRepository.findByIdAndIsActive(memberDto.getCompanyId(), YN.Y)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));

        // 2. 유효성 검증 ( 중복 아이디 검증, 이메일 중복 검증, 비밀번호 검증 )
        MemberValidator.validateMember(memberRepository, memberDto);

        // 3. Member 엔티티 생성
        Member member = Member.createMember(
                memberDto.getLoginId(),
                passwordEncoder.encode(memberDto.getPassword()),
                company,
                memberDto.getRole(),
                memberDto.getName(),
                memberDto.getEmail(),
                memberDto.getBirthDate(),
                memberDto.getPhoneNumber(),
                memberDto.getDepartment(),
                memberDto.getPosition()
        );

        // 4. 저장
        Member savedMember = memberRepository.save(member);

        // 5. DTO로 변환 후 반환
        return MemberDto.fromEntity(savedMember);
    }

    /**
     * 함수명 : createMembers
     * 여러 회원을 일괄 생성합니다.
     *
     * @param memberDtos 생성할 회원들의 요청 데이터 리스트.
     * @return 생성된 회원들의 응답 DTO 리스트.
     */
    @Transactional
    public List<MemberDto.Response> createMembers(List<MemberDto.Request> memberDtos) {
        // 1. 요청에 포함된 각 DTO 처리
        List<Member> members = memberDtos.stream().map(memberDto -> {
            // 1.1. 회사 조회
            Company company = companyRepository.findByIdAndIsActive(memberDto.getCompanyId(), YN.Y)
                    .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));

            // 1.2. 유효성 검증
            MemberValidator.validateMember(memberRepository, memberDto);

            // 1.3. Member 엔티티 생성
            return Member.createMember(
                    memberDto.getLoginId(),
                    passwordEncoder.encode(memberDto.getPassword()),
                    company,
                    memberDto.getRole(),
                    memberDto.getName(),
                    memberDto.getEmail(),
                    memberDto.getBirthDate(),
                    memberDto.getPhoneNumber(),
                    memberDto.getDepartment(),
                    memberDto.getPosition()
            );
        }).toList();

        // 2. 일괄 저장
        List<Member> savedMembers = memberRepository.saveAll(members);

        // 3. 저장된 엔티티를 DTO로 변환 후 반환
        return savedMembers.stream()
                .map(MemberDto::fromEntity)
                .toList();
    }

    /**
     * 함수명 : updateMember
     * 회원 정보를 수정합니다.
     *
     * @param loginId       수정할 회원의 ID.
     * @param memberDto 수정할 회원의 요청 데이터.
     * @return 수정된 회원의 응답 DTO.
     */
    @Transactional
    public MemberDto.Response updateMember(String loginId, MemberUpdate.PatchRequest memberDto){

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Company company = companyRepository.findByIdAndIsActive(memberDto.getCompanyId(), YN.Y)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));

        member.updateMember(memberDto.getName(), member.getEmail(), memberDto.getPhoneNumber(), memberDto.getRole(), company,
                memberDto.getDepartment(), memberDto.getPosition());

        return MemberDto.fromEntity(member);
    }

    /**
     * 함수명 : deleteMember
     * 회원을 삭제(상태 변경)합니다.
     *
     * @param loginId 삭제할 회원의 ID.
     */
    @Transactional
    public void deleteMember(String loginId) {
        // 회원 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 상태 확인 및 예외 처리
        switch (member.getStatus()) {
            case INACTIVE:
                throw new BusinessException(ErrorCode.MEMBER_INACTIVE);
            case SUSPENDED:
                throw new BusinessException(ErrorCode.MEMBER_SUSPENDED);
            case WITHDRAW:
                throw new BusinessException(ErrorCode.MEMBER_WITHDRAW);
            default:
                break;
        }

        // 삭제 상태 변경
        member.deleteMember();
    }

    /**
     * 함수명 : resetPassword
     * 회원 비밀번호를 초기화합니다.
     *
     * @param loginId 비밀번호를 초기화할 회원의 로그인 ID.
     * @return 초기화된 임시 비밀번호.
     */
    @Transactional
    public MemberUpdate.PatchResponse resetPassword(String loginId) {
        // 1. 회원 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 2. 임시 비밀번호 생성
        String temporaryPassword = generateTemporaryPassword();

        // 3. 비밀번호 암호화 후 저장
        member.resetPassword(passwordEncoder.encode(temporaryPassword));

        // 4. 생성된 비밀번호 반환
        return new MemberUpdate.PatchResponse(loginId, temporaryPassword);
    }


    /**
     * 함수명 : generateTemporaryPassword
     * 대문자, 소문자, 숫자, 특수문자를 포함한 12자리 임시 비밀번호를 생성합니다.
     *
     * @return 생성된 임시 비밀번호.
     */
    private String generateTemporaryPassword() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

    /**
     * 함수명 : getToken
     * 사용자 인증 정보를 확인하고, JWT 토큰을 생성하여 반환합니다.
     *
     * @param loginId 로그인에 사용할 사용자 ID
     * @param pwd     로그인에 사용할 사용자 비밀번호
     * @return 생성된 JWT 토큰
     */
    public TokenResponse getToken(String loginId, String pwd) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, pwd);

        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationMngrBuilder.getObject().authenticate(authenticationToken);

        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성 후 반환
        TokenResponse tokens = tokenProvider.createTokens(authentication);

        return tokens;
    }

}
