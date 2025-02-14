package com.seveneleven.util.file.dto;

import com.seveneleven.entity.file.FileMetadata;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class FileMetadataResponse {
    private Long id;                 //통합 파일 id
    private String category;         //파일 카테고리
    private Long referenceId;        //참조 ID
    private String displayTitle;     //유저에게 보여질 파일 이름
    private String title;            //저장된 파일 이름(UUID)
    private String contentType;      //MIME 타입
    private String format;           //파일 확장자(포맷)
    private Double size;             //파일 사이즈
    private String path;             //파일 경로
    private Long createdBy;          //등록자 ID
    private LocalDateTime createdAt; //등록일시

    //Entity -> DTO
    public static Optional<FileMetadataResponse> toDto(FileMetadata metadata) {
        return Optional.ofNullable(metadata)
                .map(m -> {
                    FileMetadataResponse dto = new FileMetadataResponse();
                    dto.id = m.getId();
                    dto.category = m.getCategory().name();
                    dto.referenceId = m.getReferenceId();
                    dto.displayTitle = m.getDisplayTitle();
                    dto.title = m.getTitle();
                    dto.contentType = m.getContentType();
                    dto.format = m.getFileFormat();
                    dto.size = m.getFileSize();
                    dto.path = m.getFilePath();
                    dto.createdBy = m.getCreatedBy();
                    dto.createdAt = m.getCreatedAt();
                    return dto;
                });
    }

    public static FileMetadataResponse toResponse(FileMetadata m) {
        FileMetadataResponse dto = new FileMetadataResponse();
        dto.id = m.getId();
        dto.category = m.getCategory().name();
        dto.referenceId = m.getReferenceId();
        dto.displayTitle = m.getDisplayTitle();
        dto.title = m.getTitle();
        dto.contentType = m.getContentType();
        dto.format = m.getFileFormat();
        dto.size = m.getFileSize();
        dto.path = m.getFilePath();
        dto.createdBy = m.getCreatedBy();
        dto.createdAt = m.getCreatedAt();
        return dto;
    }
}
