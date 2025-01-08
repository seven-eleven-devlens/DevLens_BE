package com.seveneleven.devlens.domain.member.repository;

import com.seveneleven.devlens.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
