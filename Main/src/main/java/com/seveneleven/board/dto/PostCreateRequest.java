package com.seveneleven.board.dto;

import com.seveneleven.entity.board.constant.PostStatus;
import com.seveneleven.entity.board.constant.TaskPriority;
import com.seveneleven.util.file.dto.LinkInput;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequest {

    @NotNull
    private Long projectStepId; // 프로젝트 단계 ID

    @Nullable
    private Long parentPostId;  // 부모 게시물 ID

    private TaskPriority priority;   // 우선순위

    @NotNull
    private PostStatus status;  // 상태

    @NotBlank
    @Size(min = 5, max = 100)
    private String title;       // 제목

    @NotBlank
    @Size(min = 10, max = 10000, message = "게시글 내용은 최소 10자 이상 최대 10,000자 이내로 작성해야 합니다.")
    private String content;     // 내용

    @Nullable
    private LocalDate deadline; // 마감일자

    private List<LinkInput> linkInputList = new ArrayList<>(); //링크 목록

    @Override
    public String toString() {
        return "PostCreateRequest{" +
                "title='" + title + '\'' +
                '}';
    }

    public Long getParentPostId() {
        if(parentPostId == null) {
            return null;
        }
        return parentPostId;
    }

}
