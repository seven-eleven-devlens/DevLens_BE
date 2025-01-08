package com.seveneleven.devlens.global.util.file.controller;

import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.util.file.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("")
    public ResponseEntity<APIResponse> getAllFiles(@RequestParam("category") String category,
                                                             @RequestParam("id") Long id) {
        APIResponse filesResponse = fileService.getFiles(category, id);
            return ResponseEntity.status(filesResponse.getCode()).body(filesResponse);
    }
}
