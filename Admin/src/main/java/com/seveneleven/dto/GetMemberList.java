package com.seveneleven.dto;

import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
public class GetMemberList {

    @Getter
    @Setter
    public class Request {
        private String name;
        private MemberStatus status;
        private Role role;
        private String loginId;

        private int page = 0;
        private int size = 10;
        private String sort = "id";
        private String direction = "asc";
    }


    @Getter
    @AllArgsConstructor
    public static class Response {
        private List<MemberDto.Response> content;      // 조회된 데이터 목록
        private int pageNumber;       // 현재 페이지 번호
        private int pageSize;         // 페이지 크기
        private long totalElements;   // 전체 데이터 개수
        private int totalPages;       // 전체 페이지 개수
        private boolean last;         // 마지막 페이지 여부
    }

}
