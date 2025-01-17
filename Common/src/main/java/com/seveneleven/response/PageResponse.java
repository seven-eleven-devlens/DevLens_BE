package com.seveneleven.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponse<T> {
    private Integer pageNum;       // 현재 페이지
    private Integer size;       // 한 페이지 사이즈
    private Long totalElements; // 전체 데이터 수
    private Integer totalPages; // 전체 페이지 수
    private List<T> content;

    private PageResponse(Integer pageNum, Integer size, Long totalElements, Integer totalPages, List<T> content) {
        this.pageNum = pageNum;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.content = content;
    }

    public static <T> PageResponse<T> createPageResponse (Page<T> page) {
        return new PageResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getContent()
        );
    }
}
