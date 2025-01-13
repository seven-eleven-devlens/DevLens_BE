package com.seveneleven.repository;

import com.seveneleven.entity.member.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    // 부서 ID로 부서 이름을 조회하는 메서드
    @Query("SELECT d.departmentName FROM Department d WHERE d.id = :positionId")
    Optional<String> findNameById(@Param("positionId") Long positionId);
}
