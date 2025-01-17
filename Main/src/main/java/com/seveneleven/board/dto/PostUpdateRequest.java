package com.seveneleven.board.dto;

import com.seveneleven.entity.board.constant.PostStatus;
import com.seveneleven.entity.global.YesNo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequest {

    private Long projectStepId;     // 프로젝트 단계 ID
    private Long parentPostId;      // 부모 게시물 ID
    private Long postId;            // 게시물 ID
    private YesNo isPinnedPost;     // 상단고정여부
    private Integer priority;       // 우선순위
    private PostStatus status;      // 상태
    private String title;           // 제목
    private String content;         // 내용
    private LocalDate deadline;     // 마감일자
    private Long modifierId;        // 수정자 ID
    private String modifierIp;      // 수정자 IP

}
