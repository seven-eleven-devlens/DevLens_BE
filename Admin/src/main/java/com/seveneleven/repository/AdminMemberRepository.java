package com.seveneleven.repository;

import com.seveneleven.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AdminMemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    Boolean existsByLoginId(String loginId);

    Boolean existsByEmail(String email);

    Optional<Member> findByLoginId(String loginId);

    Page<Member> findALl(Pageable pageable);
}
