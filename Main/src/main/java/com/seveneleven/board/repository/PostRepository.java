package com.seveneleven.board.repository;

import com.seveneleven.entity.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //
    /*
        <게시글 목록 조회>          나중에 다시 시도해보자
        조건
         - 프로젝트 단계별 조회
         - 등록일자 최신순 정렬
         - 부모게시글이 not-null인 경우,

         + 상단고정글 먼저 조회 (추후 진행)

         PostResponseDto - list<Post>, 상태, 제목, 작성자, 작성일자, 마감일자, 페이징

         content    sequence    childCount
         1          1           1
         2          2           0
         3          3           0
         4          4           0
         5          1           0
         6          1           0
         7          1           1
         8          2           0
     */

}
