package com.seveneleven.devlens.global.util.file.controller;

import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.util.file.Service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    /**
     * 1. 파일 업로드
     * @param file 업로드할 파일
     * @return 업로드된 파일 메타데이터, 성공 메시지
     */
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(
            summary = "Upload a file",
            description = "Upload a file to the server",
            responses = {
                    @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid file upload request")
            }
    )
    public ResponseEntity<Object> uploadFile(@RequestParam("file")
                                                 @Schema(type = "string", format = "binary", description = "File to upload") MultipartFile file) throws Exception {
        APIResponse uploadResponse = fileService.uploadFile(file, 1L, "POST_ATTACHMENT", 1L);

        return ResponseEntity.status(uploadResponse.getCode()).body(uploadResponse.getData());
    }
}
