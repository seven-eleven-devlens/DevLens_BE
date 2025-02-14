package com.seveneleven.util.file.dto;

import com.seveneleven.entity.file.Link;
import com.seveneleven.entity.file.constant.LinkCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class LinkResponse {
    private Long id;                    //통합 링크 Id
    private LinkCategory linkCategory;  //링크 카테고리
    private Long referenceId;           //링크 참조 Id
    private String linkTitle;           //링크명
    private String link;                //링크
    private Long createdBy;             //등록자 Id
    private LocalDateTime createdAt;    //등록일시

    //Entity -> DTO
    public static Optional<LinkResponse> toDto(Link link) {
        return Optional.ofNullable(link)
                .map(m -> {
                    LinkResponse dto = new LinkResponse();
                    dto.id = link.getId();
                    dto.linkCategory = link.getCategory();
                    dto.referenceId = link.getReferenceId();
                    dto.linkTitle = link.getLinkTitle();
                    dto.link = link.getLink();
                    dto.createdBy = link.getCreatedBy();
                    dto.createdAt = link.getCreatedAt();

                    return dto;
                });
    }

    //생성 응답용
    public static LinkResponse toResponse(Link link) {
        LinkResponse dto = new LinkResponse();
        dto.id = link.getId();
        dto.linkCategory = link.getCategory();
        dto.referenceId = link.getReferenceId();
        dto.linkTitle = link.getLinkTitle();
        dto.link = link.getLink();
        dto.createdBy = link.getCreatedBy();
        dto.createdAt = link.getCreatedAt();

        return dto;
    }
}
