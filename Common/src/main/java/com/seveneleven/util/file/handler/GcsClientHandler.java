package com.seveneleven.util.file.handler;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GcsClientHandler {

    private final Storage storage;

    @Value("${gcp.bucket-name}")
    private String bucket;

    /**
     * 고유한 파일 이름 생성(UUID)
     * GCP 버킷에 저장되는 파일은 고유 이름을 가져야 함.
     */
    public String generateUniqueFileName(String originalFileName) {
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + fileExtension;
    }

    /**
     * S3 키 생성
     * 키 = S3 저장시 파일 경로 (파일카테고리/참조ID/uuid파일명)
     */
    public String generateGcsKey(String category, Long referenceId, String fileName) {
        return category + "/" + referenceId + "/" + fileName;
    }

    /**
     * 파일 업로드
     */
    public String uploadFile(MultipartFile file, String gcsKey) {
        try {
            //Gcs 저장할 파일의 메타데이터(BlobInfo) 설정
            BlobInfo blobInfo = BlobInfo.newBuilder(bucket, gcsKey)
                    .setContentType(file.getContentType())
                    .build();

            //파일 업로드
            storage.create(blobInfo, file.getBytes());

            //업로드한 파일의 퍼블릭URL 반환
            return String.format("https://storage.googleapis.com/%s/%s", bucket, gcsKey);

        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), ErrorCode.GCP_UPLOAD_FAIL_ERROR);
        }
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String gcsKey) {
        storage.delete(bucket, gcsKey);
    }
}