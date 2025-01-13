package com.seveneleven.member.repository;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {

    // TODO - JOIN VS getCompany().getId() 검토 필요
    Optional<Long> findCompanyIdByIdAndStatus(Long memberId, MemberStatus statusCode);

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByLoginIdAndStatus(String loginId,MemberStatus statusCode);

    @EntityGraph(attributePaths = {"role"})
    Optional<Member> findOneWithAuthoritiesByLoginId(String loginId);

    @Query("SELECT m.name FROM Member m WHERE m.id = :id")
    Optional<String> findNameById(Long id);

    Optional<Member> findByIdAndStatus(Long memberId, MemberStatus statusCode);
}