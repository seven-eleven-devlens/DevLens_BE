package com.seveneleven.devlens.domain.admin.service;


import com.seveneleven.devlens.domain.admin.dto.MemberDto;
import com.seveneleven.devlens.domain.admin.dto.MemberUpdate;
import com.seveneleven.devlens.domain.admin.repository.CompanyRepository;
import com.seveneleven.devlens.domain.member.MemberValidator;
import com.seveneleven.devlens.domain.member.constant.MemberStatus;
import com.seveneleven.devlens.domain.member.constant.Role;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.member.dto.MemberSpecification;
import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.member.repository.MemberRepository;
import com.seveneleven.devlens.global.exception.BusinessException;
import com.seveneleven.devlens.global.response.ErrorCode;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
@AllArgsConstructor
public class MemberMgmtService {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

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
     * @param loginId 회원 ID.
     * @return 회원 상세 정보 DTO.
     */
    @Transactional(readOnly = true)
    public MemberDto.Response getMemberDetail(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
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
                memberDto.getDepartmentId(),
                memberDto.getPositionId()
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
                    memberDto.getDepartmentId(),
                    memberDto.getPositionId()
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
    public MemberDto.Response updateMember(String loginId, MemberUpdate.PatchRequest memberDto) {

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Company company = companyRepository.findByIdAndIsActive(memberDto.getCompanyId(), YN.Y)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));

        member.updateMember(memberDto.getName(), memberDto.getPhoneNumber(), memberDto.getRole(), company,
                memberDto.getDepartmentId(), memberDto.getPositionId());

        Member updatedMember = memberRepository.save(member);

        return MemberDto.fromEntity(updatedMember);
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
            case ACTIVE:
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

}
