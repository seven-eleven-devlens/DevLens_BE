package com.seveneleven.util.file.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LinkInput {
    private String linkTitle;  //링크명(nullable)
    @Size(min = 1, max = 1000, message = "링크는 1자 이상 1000자까지 입력 가능합니다.")
    private String link;       //링크
}
