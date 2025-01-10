package com.seveneleven.devlens.domain.admin.repository;

import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.domain.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectName(String projectName);

    Page<Project> findByCustomerOrDeveloper(Pageable pageable, Company customer, Company developer);
}
