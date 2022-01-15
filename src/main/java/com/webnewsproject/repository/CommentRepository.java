package com.webnewsproject.repository;

import com.webnewsproject.domain.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comments, Integer> {

    @Query("select c from Comments c where c.news.newsId = ?1 and c.user.username = ?2")
    Comments findComment(int newsId, String username);

    @Query("select c from Comments c where c.news.newsId = ?1")
    List<Comments> findAllByPostId(int id);

    @Query("select count(c.commentId) from Comments c")
    int countComment();

    @Query("select c from Comments c where concat(c.user.fullname, c.content)" +
            "like %?1% ")
    Page<Comments> search(String keyword, Pageable pageable);
}
