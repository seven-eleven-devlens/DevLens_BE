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
    @Query("SELECT m.company.id FROM Member m where m.id = :memberId AND m.status = :statusCode")
    Optional<Long> findCompanyIdByIdAndStatus(Long memberId, MemberStatus statusCode);

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByLoginIdAndStatus(String loginId,MemberStatus statusCode);

    @Query("SELECT m.name FROM Member m WHERE m.id = :id")
    Optional<String> findNameById(Long id);

    Optional<Member> findByIdAndStatus(Long memberId, MemberStatus statusCode);

    Optional<Member> findByEmailAndStatus(String email, MemberStatus statusCode);

}