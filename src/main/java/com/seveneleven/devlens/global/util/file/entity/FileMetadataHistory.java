package com.seveneleven.devlens.global.util.file.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "file_metadata_history")
public class FileMetadataHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_metadata_history_id")
    private Long id; // 통합 파일 이력 ID

    @Column(name = "history_type", nullable = false)
    private String historyType; // 이력 유형(등록/삭제)

    @Column(name = "file_category", nullable = false)
    // 파일 카테고리(회사로고, 계정프로필이미지, 프로젝트 정보이미지, 게시물파일, 체크승인요청파일, 체크승인요청거절사유파일)
    private String fileCategory;

    @Column(name = "reference_identifier", length = 300, nullable = false)
    private String referenceIdentifier; // 참조 구분자
    //회사로고 - 회사명, 계정프로필 - 계정이메일, 프로젝트 이미지 - 프로젝트명, 게시물 파일 - 게시물 제목
    // 체크승인요청파일 - 요청일시, 체크 승인요청 거절사유 - 처리일시

    @Column(name = "file_display_title", length = 300, nullable = false)
    private String fileDisplayTitle; //유저에게 보이는 파일명

    @Column(name = "file_title", length = 50,  nullable = false)
    private String fileTitle; //S3 저장명(UUID)

    @Column(name = "writer_email", nullable = false)
    private String writerEmail; // 등록자 이메일

    @Column(name = "writer_name", length = 200, nullable = false)
    private String writerName; // 등록자 이름

    @Column(name = "writer_authority", length = 50, nullable = false)
    private String writerAuthority; // 등록자 권한

    @Column(name = "written_at", nullable = false)
    private LocalDateTime writtenAt; // 등록 일시

    @Column(name = "content_type", nullable = false)
    private String contentType; //MIME 타입

    @Column(name = "file_format", length = 50, nullable = false)
    private String fileFormat; // 파일 확장자(포맷)

    @Column(name = "file_size", precision = 10, nullable = false)
    private Float fileSize; // 파일 크기 (KB)

    @Column(name = "file_path", length = 1000, nullable = false)
    private String filePath; // 파일 경로

    //시스템컬럼
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy; // 최초 작성자 ID

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 최초 등록 일시
}