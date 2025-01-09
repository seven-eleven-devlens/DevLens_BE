package com.seveneleven.devlens.domain.member.repository;


import com.seveneleven.devlens.domain.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {

    Optional<Member> findByLoginId(String loginId);

    @EntityGraph(attributePaths = {"role"})
    Optional<Member> findOneWithAuthoritiesByLoginId(String loginId);

    Boolean existsByLoginId(String LoginId);

    Boolean existsByEmail(String email);
}