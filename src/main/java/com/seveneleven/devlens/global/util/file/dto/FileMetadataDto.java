//package com.seveneleven.devlens.global.util.file.dto;
//
//import com.seveneleven.devlens.global.util.file.entity.FileMetadata;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Getter
//
//
//@Builder(toBuilder = true)
//@NoArgsConstructor
//@AllArgsConstructor
//public class FileMetadataDto {
//    //TODO) validation
//    private Long id;                 //통합 파일 id
//    private String fileCategory;     //파일 카테고리
//    private Long referenceId;        //참조 ID
//    private String fileDisplayTitle; //유저에게 보여질 파일 이름
//    private String fileTitle;        //저장된 파일 이름(UUID)
//    private Long writtenBy;          //등록자 ID
//    private LocalDateTime writtenAt; //등록일시
//    private String contentType;      //MIME 타입
//    private String fileFormat;       //파일 확장자(포맷)
//    private Double fileSize;         //파일 사이즈
//    private String filePath;         //파일 경로
//
//    //Entity -> DTO
//    public static FileMetadataDto toDto(FileMetadata metadata) {
//        FileMetadataDto dto = new FileMetadataDto();
//        dto.setId(metadata.getId());
//        dto.setFileCategory(metadata.getFileCategory());
//        dto.setReferenceId(metadata.getReferenceId());
//        dto.setFileDisplayTitle(metadata.getFileDisplayTitle());
//        dto.setFileTitle(metadata.getFileTitle());
//        dto.setWrittenBy(metadata.getWrittenBy());
//        dto.setWrittenAt(LocalDateTime.now());
//        dto.setFileSize(metadata.getFileSize());
//        dto.setFilePath(metadata.getFilePath());
//        dto.setContentType(metadata.getContentType());
//
//        return dto;
//    }
//
//
//}
