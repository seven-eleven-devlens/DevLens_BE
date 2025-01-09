package com.seveneleven.devlens.domain.board.dto;

import com.seveneleven.devlens.domain.member.constant.YN;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequest {

    @NotNull
    private Long projectStepId; // 프로젝트 단계 ID
    private Long parentPostId;  // 부모 게시물 ID
    private YN isPinnedPost;    // 상단고정여부
    private Integer priority;   // 우선순위
    private PostStatus status;  // 상태

    @NotBlank
    private String title;       // 제목

    @NotBlank
    private String content;     // 내용
    private LocalDate deadline; // 마감일자

    @NotNull
    private Long registerId;    // 등록자 ID

    @NotBlank
    private String registerIp;  // 등록자 IP

    @NotNull
    private LocalDateTime registerDate; // 등록일시

}
