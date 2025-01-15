package com.seveneleven.board.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> {
    private Integer page;       // 현재 페이지
    private Integer size;       // 한 페이지 사이즈
    private Long totalElements; // 전체 데이터 수
    private Integer totalPages; // 전체 페이지 수
    private List<T> content;

    public PageResponse(Integer page, Integer size, Long totalElements, Integer totalPages, List<T> content) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.content = content;
    }

}
