package com.seveneleven.devlens.domain.board.repository;

import com.seveneleven.devlens.domain.board.entity.PostHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostHistoryRepository extends JpaRepository<PostHistory, Long> {
}
