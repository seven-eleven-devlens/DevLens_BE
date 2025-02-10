package com.seveneleven.util.file.repository;

import com.seveneleven.entity.file.FileMetadataHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataHistoryRepository extends JpaRepository<FileMetadataHistory, Long> {
}
