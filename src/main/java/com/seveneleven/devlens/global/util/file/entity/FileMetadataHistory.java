//package com.seveneleven.devlens.global.util.file.entity;
//
//import com.seveneleven.devlens.global.util.file.constant.FileCategory;
//import com.seveneleven.devlens.global.util.file.constant.FileHistoryType;
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@EntityListeners(AuditingEntityListener.class)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "file_metadata_history")
//public class FileMetadataHistory {
//    // Magic Numbers
//    private static final int MAX_REFERENCE_IDENTIFIER_LENGTH = 300;
//    private static final int MAX_FILE_DISPLAY_TITLE_LENGTH = 300;
//    private static final int MAX_FILE_TITLE_LENGTH = 50;
//    private static final int MAX_WRITER_NAME_LENGTH = 200;
//    private static final int MAX_WRITER_AUTHORITY_LENGTH = 50;
//    private static final int MAX_FILE_FORMAT_LENGTH = 50;
//    private static final int FILE_SIZE_PRECISION = 10;
//    private static final int MAX_FILE_PATH_LENGTH = 1000;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "file_metadata_history_id")
//    private Long id; // 통합 파일 이력 ID
//
//    @Column(name = "history_type", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private FileHistoryType historyType; // 이력 유형(등록/삭제)
//
//    @Column(name = "file_category", nullable = false)
//    @Enumerated(EnumType.STRING)
//    // 파일 카테고리(회사로고, 계정프로필이미지, 프로젝트 정보이미지, 게시물파일, 체크승인요청파일, 체크승인요청거절사유파일)
//    private FileCategory fileCategory;
//
//    @Column(name = "reference_id", nullable = false)
//    private Long referenceId; //참조 ID
//
//    // 회사로고 - 회사명, 계정프로필 - 계정이메일, 프로젝트 이미지 - 프로젝트명, 게시물 파일 - 게시물 제목
//    // 체크승인요청파일 - 요청일시, 체크 승인요청 거절사유 - 처리일시
//    @Column(name = "reference_identifier", length = MAX_REFERENCE_IDENTIFIER_LENGTH, nullable = false)
//    private String referenceIdentifier; // 참조 구분자
//
//    @Column(name = "file_display_title", length = MAX_FILE_DISPLAY_TITLE_LENGTH, nullable = false)
//    private String fileDisplayTitle; //유저에게 보이는 파일명
//
//    @Column(name = "file_title", length = MAX_FILE_TITLE_LENGTH, nullable = false)
//    private String fileTitle; //S3 저장명(UUID)
//
//    @Column(name = "writer_email", nullable = false)
//    private String writerEmail; // 파일 등록자 이메일
//
//    @Column(name = "writer_name", length = MAX_WRITER_NAME_LENGTH, nullable = false)
//    private String writerName; // 파일 등록자 이름
//
//    @Column(name = "writer_authority", length = MAX_WRITER_AUTHORITY_LENGTH, nullable = false)
//    private String writerAuthority; // 파일 등록자 권한
//
//    @Column(name = "written_at", nullable = false)
//    private LocalDateTime writtenAt; // 파일 등록 일시
//
//    @Column(name = "content_type", nullable = false)
//    private String contentType; //MIME 타입
//
//    @Column(name = "file_format", length = MAX_FILE_FORMAT_LENGTH, nullable = false)
//    private String fileFormat; // 파일 확장자(포맷)
//
//    @Column(name = "file_size", precision = FILE_SIZE_PRECISION, nullable = false)
//    private Double fileSize; // 파일 크기 (KB)
//
//    @Column(name = "file_path", length = MAX_FILE_PATH_LENGTH, nullable = false)
//    private String filePath; // 파일 경로
//
//    //시스템컬럼
//    @CreatedBy
//    @Column(name = "created_by", nullable = false, updatable = false)
//    private Long createdBy; // 이력 등록자 ID
//
//    @CreatedDate
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt; // 최초 등록 일시
//
//    public FileMetadataHistory(String historyType, String fileCategory, Long referenceId,
//                               String referenceIdentifier, String fileDisplayTitle,
//                               String fileTitle, String writerEmail, String writerName,
//                               String writerAuthority, LocalDateTime writtenAt,
//                               String contentType, String fileFormat, Double fileSize,
//                               String filePath) {
//        this.historyType = historyType;
//        this.fileCategory = fileCategory;
//        this.referenceId = referenceId;
//        this.referenceIdentifier = referenceIdentifier;
//        this.fileDisplayTitle = fileDisplayTitle;
//        this.fileTitle = fileTitle;
//        this.writerEmail = writerEmail;
//        this.writerName = writerName;
//        this.writerAuthority = writerAuthority;
//        this.writtenAt = writtenAt;
//        this.contentType = contentType;
//        this.fileFormat = fileFormat;
//        this.fileSize = fileSize;
//        this.filePath = filePath;
//    }
//
//    public static FileMetadataHistory registerHistory(FileHistoryType , String fileCategory, Long referenceId,
//                                                      String referenceIdentifier, String fileDisplayTitle,
//                                                      String fileTitle, String writerEmail, String writerName,
//                                                      String writerAuthority, LocalDateTime writtenAt,
//                                                      String contentType, String fileFormat, Double fileSize,
//                                                      String filePath){
//
//    }
//}