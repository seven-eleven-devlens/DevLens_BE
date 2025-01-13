package com.seveneleven.member.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.dto.MyPageGetMember;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private final MemberRepository memberRepository;

    /**
     * 함수명 : getMember
     * 특정 회원의 상세 정보를 조회합니다.
     *
     * @param loginId 조회할 회원의 로그인 ID.
     * @return 회원 상세 정보를 포함한 MyPageGetMember 객체.
     * @throws BusinessException 회원이 존재하지 않거나 비활성 상태일 경우 예외를 던집니다.
     */
    public MyPageGetMember getMember(String loginId) {
        MyPageGetMember response = null;
        try {
            // 회원 조회
            Member member = memberRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

            // 회원이 비활성 상태인지 확인
            if (member.getStatus() != MemberStatus.ACTIVE) {
                throw new BusinessException(ErrorCode.MEMBER_INACTIVE);
            }

            // 응답 DTO 생성 및 회사 정보 설정
            response = MyPageGetMember.fromEntity(member);
            response.setCompanyId(member.getCompany().getId());
            response.setCompanyStatus(member.getCompany().getIsActive());


        }catch(Exception e){
            e.printStackTrace();
        }

        return response;
    }
}
