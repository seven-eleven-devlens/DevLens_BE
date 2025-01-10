package com.seveneleven.devlens.domain.member.repository;

import com.seveneleven.devlens.domain.member.constant.MemberStatus;
import com.seveneleven.devlens.domain.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // TODO - JOIN VS getCompany().getId() 검토 필요
    Optional<Long> findCompanyIdByIdAndStatus(Long memberId, MemberStatus statusCode);

    Optional<Member> findByLoginId(String loginId);

    @EntityGraph(attributePaths = {"role"})
    Optional<Member> findOneWithAuthoritiesByLoginId(String loginId);
}