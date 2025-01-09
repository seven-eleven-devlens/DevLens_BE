package com.seveneleven.devlens.global.util.file.repository;

import com.seveneleven.devlens.global.util.file.constant.FileCategory;
import com.seveneleven.devlens.global.util.file.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    Optional<FileMetadata> findTopByCategoryAndReferenceIdOrderByCreatedAtDesc(FileCategory category, Long referenceId);

    List<FileMetadata> findAllByCategoryAndReferenceId(FileCategory category, Long referenceId);

}
