package com.seveneleven.devlens.domain.board.repository;

import com.seveneleven.devlens.domain.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
