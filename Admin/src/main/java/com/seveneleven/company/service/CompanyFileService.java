package com.seveneleven.company.service;

import com.seveneleven.company.repository.CompanyRepository;
import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.entity.member.Company;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.handler.FileHandler;
import com.seveneleven.util.file.dto.FileMetadataDto;
import com.seveneleven.util.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CompanyFileService {
    private final FileHandler fileHandler;
    private final CompanyRepository companyRepository;
    private final FileMetadataRepository fileMetadataRepository;

    /**
     * 1. 회사 로고 이미지 등록
     * @auth admin, super(해당 회사 대표회원)
     * @param file 업로드할 로고 이미지 파일
     * @param companyId 해당 회사 id
     * @param uploaderId 업로드 수행자 id
     */
    @Transactional
    public void uploadLogoImage(MultipartFile file, Long companyId, Long uploaderId) {
        //1. 회사 id로 존재여부 판별
        Company companyEntity = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));

        //TODO) 2. 수행자 권한 판별 validation - admin판별, super의 회사 판별

        //3. 로고 이미 존재하는지 판별
        if(fileMetadataRepository.existsByCategoryAndReferenceId(FileCategory.COMPANY_LOGO_IMAGE, companyId)){
            throw new BusinessException(ErrorCode.LOGO_ALREADY_EXIST_ERROR);
        }

        //4. S3파일 업로드, 메타데이터 테이블 저장
        fileHandler.uploadFile(file, FileCategory.COMPANY_LOGO_IMAGE, companyId);
    }

    /**
     * 2. 회사 로고 이미지 조회
     * @param companyId 해당 회사 id
     * @return fileMetadataDto S3에 저장된 파일의 메타데이터 DTO
     */
    @Transactional(readOnly = true)
    public FileMetadataDto getLogoImage(Long companyId) {
        //회사 유효성 검사
        if(!companyRepository.existsById(companyId)){
            throw new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND);
        }

        //카테고리와 참조 id 로 FileMetadata 탐색
        FileMetadata fileEntity = fileHandler.getFile(FileCategory.COMPANY_LOGO_IMAGE, companyId);

        //dto 변환 후 반환
        return FileMetadataDto.toDto(fileEntity);
    }

    /**
     * 3. 회사 로고 이미지 삭제
     * @auth admin, super(해당 회사 대표회원)
     * @param companyId 해당 회사 ID
     */
    @Transactional
    public void deleteLogoImage(Long companyId, Long uploaderId) {
        //1.회사 유효성 검사
        Company companyEntity = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));

        //TODO) 2. 삭제 수행자 판별

        //3. 삭제수행
        fileHandler.deleteFile(FileCategory.COMPANY_LOGO_IMAGE, companyId);
    }
}

