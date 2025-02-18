package com.seveneleven.member.repository;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AdminMemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    Boolean existsByLoginId(String loginId);

    Boolean existsByEmail(String email);

    Optional<Member> findByLoginId(String loginId);

    Page<Member> findAll(Pageable pageable);

    List<Member> findAllByCompany(Company company);

    Optional<Member> findByLoginIdAndStatus(String loginId, MemberStatus statusCode);

    List<Member> findMembersByCompanyIdAndStatus(Long companyId, MemberStatus statusCode);

    Optional<Member> findByIdAndStatus(Long id, MemberStatus statusCode);

    List<Member> findByStatusAndUpdatedAtBefore(MemberStatus status, LocalDateTime dateTime);

    @Modifying
    @Transactional
    @Query("DELETE FROM Member m WHERE m.status = :status AND m.updatedAt < :dateTime")
    void deleteByStatusAndUpdatedAtBefore(MemberStatus status, LocalDateTime dateTime);
}
