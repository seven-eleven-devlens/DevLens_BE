package com.seveneleven.devlens.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 1000번대 코드 : 회원 관련
    UNAUTHORIZED(1000, HttpStatus.UNAUTHORIZED, "사용자 인증이 필요합니다."),
    FORBIDDEN(1001, HttpStatus.FORBIDDEN, "사용 권한이 없습니다."),
    NOT_FOUND_TOKEN(1002, HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않은 JWT 토큰입니다."),
    EXPIRED_TOKEN(1003, HttpStatus.INTERNAL_SERVER_ERROR, "만료된 JWT 토큰입니다."),
    JWT_FILTER_ERROR(1004, HttpStatus.INTERNAL_SERVER_ERROR, "필터 처리 중 예외가 발생했습니다."),



    // 2000번대 코드 : 프로젝트 관련


    // 3000번대 코드 : DB 관련


    // 4000번대 코드 : 파일 s3 버킷 업/다운로드 관련
    S3_UPLOAD_FAIL_ERROR(4000, HttpStatus.INTERNAL_SERVER_ERROR, "S3 파일 업로드에 실패했습니다."),

    // 5000번대 코드 : 서버 내부 오류 관련
    INTERNAL_SERVER_ERROR(5001, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),


    // 10000 : 알 수 없는 예외
    UNKNOWN_ERROR(10000, HttpStatus.BAD_REQUEST, "알 수 없는 예외입니다,");

    private final Integer code;
    private final HttpStatus status;
    private final String message;

    public int getStatusCode() {
        return status.value();
    }
}
