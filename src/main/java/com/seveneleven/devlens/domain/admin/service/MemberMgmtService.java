package com.seveneleven.devlens.domain.admin.service;


import com.seveneleven.devlens.domain.admin.db.CompanyRepository;
import com.seveneleven.devlens.domain.admin.dto.MemberDto;
import com.seveneleven.devlens.domain.member.constant.MemberStatus;
import com.seveneleven.devlens.domain.member.constant.Role;
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
     * 필터 조건에 따라 회원 목록을 조회합니다.
     *
     * @param name     회원 이름 필터 (옵션).
     * @param status   회원 상태 필터 (옵션).
     * @param role     회원 역할 필터 (옵션).
     * @param loginId  로그인 ID 필터 (옵션).
     * @param pageable 페이징 정보.
     * @return 필터 조건에 맞는 회원 목록.
     */
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
     * 회원 상세 정보를 조회합니다.
     *
     * @param id 회원 ID.
     * @return 회원 상세 정보 DTO.
     */
    public MemberDto.Response getMemberDetail(String id) {
        Member member = memberRepository.findByLoginId(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return MemberDto.fromEntity(member);
    }

    /**
     * 새로운 회원을 생성합니다.
     *
     * @param memberDto 생성할 회원의 요청 데이터.
     * @return 생성된 회원의 응답 DTO.
     */
    public MemberDto.Response createMember(MemberDto.Request memberDto) {
        // 1. 회사 조회
        Company company = companyRepository.findById(memberDto.getCompanyId())
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));

        // 2. 유효성 검증 ( 중복 아이디 검증, 이메일 중복 검증, 비밀번호 검증 )
        validateMember(memberDto);

        // 3. 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());

        // 4. Member 엔티티 생성
        Member member = Member.createMember(
                memberDto.getLoginId(),
                encodedPassword,
                company,
                memberDto.getRole(),
                memberDto.getName(),
                memberDto.getEmail(),
                memberDto.getBirthDate(),
                memberDto.getPhoneNumber(),
                memberDto.getDepartmentId(),
                memberDto.getPositionId()
        );

        // 5. 저장
        Member savedMember = memberRepository.save(member);

        // 6. DTO로 변환 후 반환
        return MemberDto.fromEntity(savedMember);
    }

    /**
     * 여러 회원을 일괄 생성합니다.
     *
     * @param memberDtos 생성할 회원들의 요청 데이터 리스트.
     * @return 생성된 회원들의 응답 DTO 리스트.
     */
    public List<MemberDto.Response> createMembers(List<MemberDto.Request> memberDtos) {
        // 1. 요청에 포함된 각 DTO 처리
        List<Member> members = memberDtos.stream().map(memberDto -> {
            // 1.1. 회사 조회
            Company company = companyRepository.findById(memberDto.getCompanyId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));

            // 1.2. 유효성 검증
            validateMember(memberDto);

            // 1.3. 비밀번호 인코딩
            String encodedPassword = passwordEncoder.encode(memberDto.getPassword());

            // 1.4. Member 엔티티 생성
            return Member.createMember(
                    memberDto.getLoginId(),
                    encodedPassword,
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
     * 회원 정보를 수정합니다.
     *
     * @param id       수정할 회원의 ID.
     * @param memberDto 수정할 회원의 요청 데이터.
     * @return 수정된 회원의 응답 DTO.
     */
    public MemberDto.Response updateMember(String id, MemberDto.PatchRequest memberDto) {

        Member member = memberRepository.findByLoginId(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Company company = companyRepository.findById(memberDto.getCompanyId())
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));

        member.updateMember(memberDto.getName(), memberDto.getPhoneNumber(), memberDto.getRole(), company,
                memberDto.getDepartmentId(), memberDto.getPositionId(), memberDto.getProfileImageExists());

        Member updatedMember = memberRepository.save(member);

        return MemberDto.fromEntity(updatedMember);
    }

    /**
     * 회원을 삭제(상태 변경)합니다.
     *
     * @param id 삭제할 회원의 ID.
     */
    public void deleteMember(String id) {
        // 회원 조회
        Member member = memberRepository.findByLoginId(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 삭제 상태 변경
        member.deleteMember();

        // 저장
        memberRepository.save(member);
    }

    /**
     * 회원 비밀번호를 초기화합니다.
     *
     * @param loginId 비밀번호를 초기화할 회원의 로그인 ID.
     * @return 초기화된 임시 비밀번호.
     */
    public String resetPassword(String loginId) {
        // 1. 회원 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 2. 임시 비밀번호 생성
        String temporaryPassword = generateTemporaryPassword();

        // 3. 비밀번호 암호화 후 저장
        member.resetPassword(passwordEncoder.encode(temporaryPassword));
        memberRepository.save(member);

        // 4. 생성된 비밀번호 반환
        return temporaryPassword;
    }


    /**
     * 대문자, 소문자, 숫자, 특수문자를 포함한 12자리 임시 비밀번호를 생성합니다.
     *
     * @return 생성된 임시 비밀번호.
     */
    private String generateTemporaryPassword() {
        return RandomStringUtils.random(12, 0, 0, true, true, "!@#$%^&*()_+".toCharArray());
    }


    /**
     * 회원 생성 시 유효성 검증을 수행합니다.
     *
     * @param memberDto 검증할 회원 요청 데이터.
     */
    private void validateMember(MemberDto.Request memberDto) {
        // 로그인 ID 중복 확인
        if (memberRepository.existsByLoginId(memberDto.getLoginId())) {
            throw new BusinessException(ErrorCode.DUPLICATE_USER_ID);
        }

        // 이메일 중복 확인
        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 비밀번호 길이 검증
        if (memberDto.getPassword().length() < 8 || memberDto.getPassword().length() > 20) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD_LENGTH);
        }

        // 비밀번호 특수문자, 숫자 포함 여부 검증
        if (!memberDto.getPassword().matches("^(?=.*[0-9])(?=.*[!@#$%^&*]).+$")) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD_FORMAT);
        }
    }

}
