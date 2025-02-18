package com.seveneleven.board.repository;

import com.seveneleven.board.dto.GetCommentResponse;
import com.seveneleven.entity.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT COALESCE(MAX(c.ref), 0) FROM Comment c")
    Long findMaxRef();

    @Query("""
            SELECT c
            FROM Comment c
            WHERE c.post.id = :postId
            ORDER BY c.ref ASC, c.refOrder ASC
            """)
    List<Comment> findCommentList(@Param("postId") Long postId);

    @Query("""
            SELECT new com.seveneleven.board.dto.GetCommentResponse(
                c,
                CASE WHEN c.modifierIp IS NOT NULL THEN c.updatedAt ELSE c.createdAt END,
                CASE WHEN c.modifierIp IS NOT NULL THEN true ELSE false END,
                CASE WHEN c.createdBy = :userId THEN true ELSE false END
                )
            FROM Comment c
            WHERE c.post.id = :postId
            ORDER BY c.ref ASC, c.refOrder ASC
            """)
    List<GetCommentResponse> findIsActiveCommentList(@Param("postId") Long postId,
                                                    @Param("userId") Long userId);

    Comment findFirstByPostIdOrderByCreatedAtDesc(@Param("postId") Long postId);
}
