package com.seveneleven.devlens.domain.member.repository;


import com.seveneleven.devlens.domain.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    @EntityGraph(attributePaths = {"role"})
    Optional<Member> findOneWithAuthoritiesByLoginId(String loginId);
}