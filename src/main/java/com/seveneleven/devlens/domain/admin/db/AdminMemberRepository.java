package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<Member, Long> {
}
