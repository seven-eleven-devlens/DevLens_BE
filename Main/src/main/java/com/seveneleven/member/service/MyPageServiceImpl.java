package com.seveneleven.member.service;

import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.MemberProfileHistory;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.YN;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.dto.MyPageGetMember;
import com.seveneleven.member.dto.PatchMember;
import com.seveneleven.member.repository.CompanyRepository;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.member.repository.PasswordHistoryRepository;
import com.seveneleven.member.repository.ProfileHistoryRepository;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.dto.FileMetadataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final ProfileHistoryRepository profileHistory;
    private final MemberFileService memberFileService;

    /**
     * 함수명 : getMember
     * 특정 회원의 상세 정보를 조회합니다.
     *
     * @param loginId 조회할 회원의 로그인 ID.
     * @return 회원 상세 정보를 포함한 MyPageGetMember 객체.
     * @throws BusinessException 회원이 존재하지 않거나 비활성 상태일 경우 예외를 던집니다.
     */
    @Transactional(readOnly = true)
    public MyPageGetMember getMember(String loginId) {

        // 회원 조회
        Member member = memberRepository.findByLoginIdAndStatus(loginId, MemberStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 회원 프로필 링크 조회
        FileMetadataDto imageMetadatadto = memberFileService.getProfileImage(member.getId());
        String imageUrl = null;
        if(imageMetadatadto != null) {
            imageUrl = memberFileService.getProfileImage(member.getId()).getPath();
        }

        // 응답 DTO 생성 및 회사 정보 설정
        MyPageGetMember response = MyPageGetMember.fromEntity(member);
                        response.setCompanyId(member.getCompany().getId());
                        response.setCompanyStatus(member.getCompany().getIsActive());
                        response.setDepartment(member.getDepartment());
                        response.setPosition(member.getPosition());
                        response.setImageUrl(imageUrl);

        return response;
    }


    /**
     * 함수명 : updateMember
     * 회원 정보를 수정합니다.
     *
     * @param loginId 수정할 회원의 로그인 ID.
     * @param memberDto 수정할 회원 정보를 담은 DTO.
     * @return 회원 수정 정보를 포함한 PatchMember.Response 객체.
     */
    @Transactional
    public PatchMember.Response updateMember(String loginId, PatchMember.Request memberDto) {

        // 회원 조회
        Member member = memberRepository.findByLoginIdAndStatus(loginId, MemberStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Company company = member.getCompany();

        if(Objects.nonNull(memberDto.getCompanyId()) && memberDto.getCompanyId() != 0 ) {
             company = companyRepository.findByIdAndIsActive(memberDto.getCompanyId(), YN.Y)
                    .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));
        }

        member.updateMember(member.getName(), memberDto.getEmail(), memberDto.getPhoneNumber(), member.getRole(), company,
                memberDto.getDepartment(), memberDto.getPosition());

        Member updatedMember = memberRepository.save(member);

        // 응답 DTO 생성 및 회사 정보 설정
        PatchMember.Response response = PatchMember.fromEntity(updatedMember);
                             response.setCompanyId(company.getId());
                             response.setDepartment(memberDto.getDepartment());
                             response.setPosition(memberDto.getPosition());

        // 회원 프로필 수정 이력 생성
        profileHistory.save(MemberProfileHistory.createProfileHistory(updatedMember));

        return response;
    }

    /**
     * 함수명 : deleteMember
     * 회원을 탈퇴(상태 변경) 메서드 입니다.
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

        // 탈퇴 상태 변경
        member.withDrawMember();
    }
}
