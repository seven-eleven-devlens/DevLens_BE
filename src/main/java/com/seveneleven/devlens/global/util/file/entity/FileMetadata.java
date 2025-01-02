package com.seveneleven.devlens.global.util.file.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "file_metadata")
public class FileMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_metadata_id")
    private Long id; //통합 파일 ID

    @Column(name = "file_category", nullable = false)
    //파일 카테고리 (회사이미지, 계정프로필이미지, 프로젝트 정보이미지, 게시뭂 파일, 체크승인요청파일, 체크 승인요청거절사유파일)
    private String fileCategory;

    @Column(name = "reference_id", nullable = false)
    private Long referenceId; //참조 ID

    @Column(name = "file_display_title", length = 300, nullable = false)
    private String fileDisplayTitle; //유저에게 보이는 파일명

    @Column(name = "file_title", length = 50,  nullable = false)
    private String fileTitle; //저장된 파일 이름(UUID)

    //TODO) 회원 ID JoinColumn, Audit
    @CreatedBy
    @Column(name = "written_by", updatable = false, nullable = false)
    private Long writtenBy; //등록자 ID

    @CreatedDate
    @Column(name = "written_at", updatable = false, nullable = false)
    private LocalDateTime writtenAt; //등록 일시

    @Column(name = "content_type", nullable = false)
    private String contentType; //MIME 타입

    @Column(name = "file_format", length = 50, nullable = false)
    private String fileFormat; //파일 확장자 (포맷)

    @Column(name = "file_size", precision = 10,  nullable = false)
    private Double fileSize; //파일 크기 (KB 단위)

    @Column(name = "file_path", length = 1000, nullable = false)
    private String filePath; //파일 경로

    //시스템 컬럼
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy; //최초 작성자 ID

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; //최초 등록 일시
}