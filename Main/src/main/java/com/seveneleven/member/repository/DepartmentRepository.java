package com.seveneleven.member.repository;

import com.seveneleven.entity.member.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Company, Long> {

    @Query("SELECT d.departmentName FROM Department d WHERE d.id = :departmentId AND d.isActive = 'Y'")
    String findDepartmentNameByIdAndIsActive(@Param("departmentId") Long departmentId);
}
