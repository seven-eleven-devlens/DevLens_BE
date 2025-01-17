package com.seveneleven.util.file.dto;

import com.seveneleven.entity.file.FileMetadata;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FileMetadataDto {
    //TODO) validation
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
    public static FileMetadataDto toDto(FileMetadata metadata) {
        if(metadata == null){
            return null;
        }

        FileMetadataDto dto = new FileMetadataDto();
        dto.id = metadata.getId();
        dto.category = metadata.getCategory().name();
        dto.referenceId = metadata.getReferenceId();
        dto.displayTitle = metadata.getDisplayTitle();
        dto.title = metadata.getTitle();
        dto.contentType = metadata.getContentType();
        dto.format = metadata.getFileFormat();
        dto.size = metadata.getFileSize();
        dto.path = metadata.getFilePath();
        dto.createdBy = metadata.getCreatedBy();
        dto.createdAt = metadata.getCreatedAt();

        return dto;
    }
}
