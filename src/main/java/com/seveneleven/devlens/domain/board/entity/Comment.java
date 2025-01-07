package com.seveneleven.devlens.domain.board.entity;

import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends BaseEntity {

    /*
        id : 댓글 ID
        postId : 게시물 ID
        parentCommentId : 부모 댓글 ID
        isActive : 사용 유무 (Y, N)
        content : 내용
        registerIp : 등록자 IP
        modifierIp : 수정자 IP
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, referencedColumnName = "id")
    private Post postId;

    @JoinColumn(name = "parent_comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentCommentId;

    @Column(name = "is_active", nullable = false)
    private YN isActive;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "register_ip", length = 50)
    private String registerIp;

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp;

}