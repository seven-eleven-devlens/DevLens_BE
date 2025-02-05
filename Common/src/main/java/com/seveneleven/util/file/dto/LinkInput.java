package com.seveneleven.util.file.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LinkInput {
    private String linkTitle;           //링크명
    @Size(max = 1000, message = "링크는 최대 1000자까지 입력 가능합니다.")
    private String link;                //링크

    public static LinkInput toLinkPayload(String linkTitle, String link) {
        if(link == null){
            return null;
        }

        LinkInput dto = new LinkInput();
        dto.linkTitle = linkTitle;
        dto.link = link;

        return dto;
    }
}
