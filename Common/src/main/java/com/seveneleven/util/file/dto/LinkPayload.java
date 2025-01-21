package com.seveneleven.util.file.dto;

import com.seveneleven.entity.file.Link;
import com.seveneleven.entity.file.constant.LinkCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LinkPayload {
    //TODO) validation
    private LinkCategory linkCategory;  //링크 카테고리
    private Long referenceId;           //링크 참조 Id
    private String linkTitle;           //링크명
    private String link;                //링크

    //input -> DTO
    public static LinkPayload toLinkPayload(LinkCategory linkCategory, Long referenceId, String linkTitle, String link) {
        if(link == null){
            return null;
        }

        LinkPayload dto = new LinkPayload();
        dto.linkCategory = linkCategory;
        dto.referenceId = referenceId;
        dto.linkTitle = linkTitle;
        dto.link = link;

        return dto;
    }
}
