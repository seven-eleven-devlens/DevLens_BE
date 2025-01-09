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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MemberMgmtService {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

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

    public MemberDto.Response getMemberDetail(String id) {
        Member member = memberRepository.findByLoginId(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return MemberDto.fromEntity(member);
    }

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

    // 계정 생성 검증
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
