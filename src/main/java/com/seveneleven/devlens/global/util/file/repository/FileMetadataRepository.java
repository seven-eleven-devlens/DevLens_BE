package com.seveneleven.devlens.global.util.file.repository;

import com.seveneleven.devlens.global.util.file.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

}
