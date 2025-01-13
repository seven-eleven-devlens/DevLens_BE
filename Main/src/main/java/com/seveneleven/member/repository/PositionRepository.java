package com.seveneleven.member.repository;

import com.seveneleven.entity.member.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PositionRepository extends JpaRepository<Company, Long> {
    @Query("SELECT p.positionName FROM Position p WHERE p.id = :positionId AND p.isActive = 'Y'")
    String findPositionNameByIdAndIsActive(@Param("positionId") Long positionId);
}
