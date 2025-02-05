package com.seveneleven.member.repository;

import com.seveneleven.entity.member.MemberPasswordResetHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordHistoryRepository  extends JpaRepository<MemberPasswordResetHistory, Long> {
}
