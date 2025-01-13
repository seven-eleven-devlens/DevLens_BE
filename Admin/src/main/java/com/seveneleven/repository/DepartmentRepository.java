package com.seveneleven.repository;

import com.seveneleven.entity.member.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Company, Long> {
    // 부서 ID로 부서 이름을 조회하는 메서드
    @Query("SELECT d.departmentName FROM Department d WHERE d.id = :departmentId")
    Optional<String> findNameById(@Param("departmentId") Long departmentId);
}
