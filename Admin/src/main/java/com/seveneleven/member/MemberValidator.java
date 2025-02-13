package com.seveneleven.member;

import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.dto.MemberDto;
import com.seveneleven.member.service.AdminMemberReader;
import com.seveneleven.response.ErrorCode;

import java.util.regex.Pattern;

public class MemberValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[!@#$%^&*]).+$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    /**
     * 함수명 : validateMember
     * 회원 생성 시 유효성 검증을 수행합니다.
     *
     * @param memberDto 검증할 회원 요청 데이터.
     */
    public static void validateMember(AdminMemberReader adminMemberReader, MemberDto.Request memberDto) {

        // 로그인 ID 길이 검증
        if (memberDto.getLoginId().length() < 4 || memberDto.getLoginId().length() > 12) {
            throw new BusinessException(ErrorCode.INVALID_LOGIN_ID_LENGTH);
        }

        // 로그인 ID 중복 확인
        if (adminMemberReader.getExistsByLoginId(memberDto.getLoginId())) {
            throw new BusinessException(ErrorCode.DUPLICATE_USER_ID);
        }

        // 이메일 중복 확인
        if (adminMemberReader.getExistsByEmail(memberDto.getEmail())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 이메일 형식 검증 추가
        if (!EMAIL_PATTERN.matcher(memberDto.getEmail()).matches()) {
            throw new BusinessException(ErrorCode.INVALID_EMAIL_FORMAT);
        }

        // 비밀번호 길이 검증
        if (memberDto.getPassword().length() < 8 || memberDto.getPassword().length() > 20) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD_LENGTH);
        }

        // 비밀번호 특수문자, 숫자 포함 여부 검증
        if (!PASSWORD_PATTERN.matcher(memberDto.getPassword()).matches()) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD_FORMAT);
        }
    }
}
