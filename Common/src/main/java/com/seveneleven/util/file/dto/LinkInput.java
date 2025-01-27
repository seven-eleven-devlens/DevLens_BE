package com.seveneleven.util.file.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LinkInput {
    //TODO) validation
    private String linkTitle;           //링크명
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
