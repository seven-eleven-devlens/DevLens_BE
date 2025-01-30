package com.seveneleven.board.dto;

import com.seveneleven.entity.board.constant.PostStatus;
import com.seveneleven.entity.board.constant.TaskPriority;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.validation.ValidEnum;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequest {

    @NotNull
    private Long projectStepId;     // 프로젝트 단계 ID

    @Nullable
    private Long parentPostId;      // 부모 게시물 ID

    @NotNull
    private Long postId;            // 게시물 ID

    @NotNull
    @ValidEnum(enumClass = YesNo.class)
    private YesNo isPinnedPost;     // 상단고정여부

    @ValidEnum(enumClass = TaskPriority.class, message = "유효하지 않은 우선순위 값입니다.")
    private TaskPriority priority;  // 우선순위

    @NotNull
    @ValidEnum(enumClass = PostStatus.class, message = "유효하지 않은 상태값입니다.")
    private PostStatus status;      // 상태

    @NotBlank
    @Size(min = 5, max = 100)
    private String title;           // 제목

    @NotBlank
    @Size(min = 10, max = 10000, message = "게시글 내용은 최소 10자 이상 최대 10,000자 이내로 작성해야 합니다.")
    private String content;         // 내용

    @Nullable
    private LocalDate deadline;     // 마감일자

    @NotNull
    private Long modifierId;        // 수정자 ID
}
