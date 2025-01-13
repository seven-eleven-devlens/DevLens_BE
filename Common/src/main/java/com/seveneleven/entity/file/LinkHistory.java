package com.seveneleven.entity.file;

import com.seveneleven.entity.file.constant.LinkCategory;
import com.seveneleven.entity.file.constant.LinkHistoryType;
import com.seveneleven.entity.member.constant.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "link_history")
public class LinkHistory {
    //Magic Number
    private static final int MAX_REFERENCE_IDENTIFIER_LENGTH = 300;
    private static final int MAX_WRITER_NAME_LENGTH = 200;
    private static final int MAX_WRITER_AUTHORITY_LENGTH = 50;
    private static final int MAX_LINK_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 통합 링크 이력 ID

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LinkHistoryType type; // 이력 유형(등록/삭제)

    @Column(name = "link_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private LinkCategory linkCategory; //링크 카테고리

    @Column(name = "reference_id", nullable = false)
    private Long referenceId; //참조 ID

    @Column(name = "reference_identifier", length = MAX_REFERENCE_IDENTIFIER_LENGTH, nullable = false)
    private String referenceIdentifier; // 참조 구분자

    @Column(name = "writer_email", nullable = false)
    private String writerEmail; // 등록자 이메일

    @Column(name = "writer_name", length = MAX_WRITER_NAME_LENGTH, nullable = false)
    private String writerName; // 등록자 이름

    @Column(name = "writer_authority", length = MAX_WRITER_AUTHORITY_LENGTH, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role writerAuthority; // 등록자 권한

    @Column(name = "written_at", nullable = false)
    private LocalDateTime writtenAt; // 링크 등록 일시

    @Column(name = "link", length = MAX_LINK_LENGTH)
    private String link; // 링크

    //시스템 컬럼
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy; // 이력 작성자 ID

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 이력 등록 일시

    //공통 생성 메서드
    private static LinkHistory createHistory(LinkHistoryType type,
                                             LinkCategory linkCategory,
                                             Long referenceId,
                                             String referenceIdentifier,
                                             String writerEmail,
                                             String writerName,
                                             Role writerAuthority,
                                             LocalDateTime writtenAt,
                                             String link){

        LinkHistory history = new LinkHistory();
        history.type = type;
        history.linkCategory = linkCategory;
        history.referenceId = referenceId;
        history.referenceIdentifier = referenceIdentifier;
        history.writerEmail = writerEmail;
        history.writerName = writerName;
        history.writerAuthority = writerAuthority;
        history.writtenAt = writtenAt;
        history.link = link;

        return history;
    }

    //파일 등록 이력 생성
    public static LinkHistory createRegisterHistory(LinkCategory linkCategory,
                                                Long referenceId,
                                                String referenceIdentifier,
                                                String writerEmail,
                                                String writerName,
                                                Role writerAuthority,
                                                LocalDateTime writtenAt,
                                                String link){

        return createHistory(LinkHistoryType.REGISTER,
                linkCategory, referenceId, referenceIdentifier,
                writerEmail, writerName, writerAuthority, writtenAt, link);
    }

    //파일 삭제 이력 생성
    public static LinkHistory createDeleteHistory(LinkCategory linkCategory,
                                                Long referenceId,
                                                String referenceIdentifier,
                                                String writerEmail,
                                                String writerName,
                                                Role writerAuthority,
                                                LocalDateTime writtenAt,
                                                String link){

        return createHistory(LinkHistoryType.DELETE,
                linkCategory, referenceId, referenceIdentifier,
                writerEmail, writerName, writerAuthority, writtenAt, link);
    }

}
