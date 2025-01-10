package com.seveneleven.board.repository;

import com.seveneleven.entity.board.PostHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostHistoryRepository extends JpaRepository<PostHistory, Long> {
}
