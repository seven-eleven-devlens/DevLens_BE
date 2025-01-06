//package com.seveneleven.devlens.global.util.file.controller;
//
//import com.seveneleven.devlens.global.response.APIResponse;
//import com.seveneleven.devlens.global.util.file.Service.FileService;
//import com.seveneleven.devlens.global.util.file.dto.FileMetadataDto;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api/files")
//@RequiredArgsConstructor
//public class FileController {
//    private final FileService fileService;
//
//    /**
//     * 수행 순서
//     * 1. 컨트롤러에서 파일을 받는다.
//     * 2. 컨트롤러에서 서비스단으로 전달
//     * 3. 서비스단에서 로컬 환경에 이미지 저장
//     * 4. 로컬 환경 파일 S3에 전송
//     * 5. S3로 부터 저장 경로 받기
//     * 6. 로컬 환경 파일 삭제
//     * 7. 저장 경로 컨트롤러단으로 전달
//     * 8. 프론트에게 Response 보내기
//     * 9. 이력 등록
//     */
//
//    /**
//     * 1. 파일 업로드
//     * @param file 업로드할 파일
//     * @return 업로드된 파일 메타데이터, 성공 메시지
//     */
//    @PostMapping(value = "/upload", consumes = "multipart/form-data")
//    @Operation(
//            summary = "Upload a file",
//            description = "Upload a file to the server",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
//                    @ApiResponse(responseCode = "400", description = "Invalid file upload request")
//            }
//    )
//    public ResponseEntity<Object> uploadFile(@RequestParam("file")  @Schema(type = "string", format = "binary", description = "File to upload") MultipartFile file) throws Exception {
//         APIResponse uploadResponse = fileService.uploadFile(file, 1L, "POST_ATTACHMENT", 1L);
//
//        return ResponseEntity.status(uploadResponse.getCode()).body(uploadResponse.getData());
//    }
//}
