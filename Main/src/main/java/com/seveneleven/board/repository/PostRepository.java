package com.seveneleven.board.repository;

import com.seveneleven.entity.board.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // REF의 최대값 구하기
    @Query("SELECT COALESCE(MAX(p.ref), 0) FROM Post p")
    Long findMaxRef();

    // 부모게시글의 REF 구하기
    Optional<Long> findRefById(Long parentPostId);

    // 그룹 내 refOrder 최대값 구하기
    @Query("SELECT MAX(p.refOrder) FROM Post p WHERE p.parentPost.id = :parentPostId")
    Optional<Integer> findMaxRefOrderByParentPostId(@Param("parentPostId") Long parentPostId);

    // 프로젝트 단게별 게시글 목록 조회
    @Query("""
    SELECT p
    FROM Post p
    WHERE p.projectStep.id = :projectStepId
    AND (
        (:filter = 'TITLE' AND p.title LIKE CONCAT('%', :keyword, '%')) OR
        (:filter = 'CONTENT' AND p.content LIKE CONCAT('%', :keyword, '%')) OR
        (:filter = 'WRITER' AND p.writer LIKE CONCAT('%', :keyword, '%')) OR
        ((:filter IS NULL OR :filter = 'ALL') AND
            (:keyword IS NULL OR p.title LIKE CONCAT('%', :keyword, '%') OR
             p.content LIKE CONCAT('%', :keyword, '%') OR
             p.writer LIKE CONCAT('%', :keyword, '%'))
        )
    )
    """)
    Page<Post> findAllByProjectStepId2(@Param("projectStepId") Long projectStepId,
                                      @Param("keyword") String keyword,
                                      @Param("filter") String filter,
                                      Pageable pageable);
}