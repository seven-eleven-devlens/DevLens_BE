package com.seveneleven.util.file.repository;

import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.entity.file.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    //조회
    Optional<FileMetadata> findByCategoryAndReferenceId(FileCategory category, Long referenceId);
    List<FileMetadata> findAllByCategoryAndReferenceId(FileCategory category, Long referenceId);
    Boolean existsByCategoryAndReferenceId(FileCategory category, Long referenceId);
    Integer countByCategoryAndReferenceId(FileCategory category, Long referenceId);

    @Query("""
            SELECT f FROM FileMetadata f 
            WHERE f.category = :category 
              AND f.referenceId IN :referenceIds
           """)
    List<FileMetadata> findByCategoryAndReferenceIds(@Param("category") FileCategory category,
                                                     @Param("referenceIds") Set<Long> referenceIds);

}
