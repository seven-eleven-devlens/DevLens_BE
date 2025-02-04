package com.seveneleven.member.repository;

import com.seveneleven.entity.member.MemberProfileHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileHistoryRepository extends JpaRepository<MemberProfileHistory, Long> {
}
