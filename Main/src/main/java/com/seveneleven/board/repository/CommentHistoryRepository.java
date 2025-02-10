package com.seveneleven.board.repository;

import com.seveneleven.entity.board.CommentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentHistoryRepository extends JpaRepository<CommentHistory, Long> {
}