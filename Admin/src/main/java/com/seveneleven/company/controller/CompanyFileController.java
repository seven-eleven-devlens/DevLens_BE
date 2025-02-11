package com.seveneleven.company.controller;

import com.seveneleven.company.service.CompanyFileService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.file.dto.FileMetadataResponse;
import com.seveneleven.util.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyFileController {
    private final CompanyFileService companyFileService;

    /**
     * 1. 회사 로고 이미지 업로드
     * 등록된 이미지가 있는 경우 새 이미지로 등록 된다.
     * @param file 업로드할 이미지 파일
     * @return 업로드된 파일 메타데이터, 성공 메시지
     */
    @PostMapping(value = "/{companyId}/logo-image", consumes = "multipart/form-data")
    public ResponseEntity<APIResponse> uploadFile(@PathVariable("companyId") Long companyId,
                                                  @RequestParam("file") MultipartFile file,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long uploaderId = userDetails.getId();

        companyFileService.uploadLogoImage(file, companyId, uploaderId);

        return ResponseEntity
                .status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED));
    }

    /**
     * 2. 회사 로고 조회
     * @param companyId 해당 회사 id
     * @return 해당 회사의 로고 이미지 메타데이터
     */
    @GetMapping(value = "/{companyId}/logo-image")
    public ResponseEntity<APIResponse> getCompanyLogo(@PathVariable("companyId") Long companyId) {

        FileMetadataResponse logoImage = companyFileService.getLogoImage(companyId);

        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, logoImage));
    }

    /**
     * 3. 회사 로고 삭제
     * @param companyId 해당 회사 id
     * @param userDetails 수행자 정보
     * @return ResponseEntity<APIResponse<SuccessCode>>
     */
    @DeleteMapping(value = "/{companyId}/logo-image")
    public ResponseEntity<APIResponse<SuccessCode>> deleteLogo(@PathVariable("companyId") Long companyId,
                                                               @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long uploaderId = userDetails.getId();

        companyFileService.deleteLogoImage(companyId, uploaderId);

        return ResponseEntity
                .status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }

}
