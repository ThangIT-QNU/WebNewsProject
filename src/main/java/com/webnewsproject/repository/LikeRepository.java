package com.webnewsproject.repository;

import com.webnewsproject.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LikeRepository extends JpaRepository<Likes, Integer> {

    @Query(nativeQuery = true, value = "select count(like_id) from Likes where news_id = ?1")
    Integer likeNumber(int newsId);

    @Query("select l from Likes l where l.news.newsId=?1 and l.user.username=?2")
    Likes findLike(int newsId, String username);

//    @Query(nativeQuery = true, value = "select News.title, count(like_id), max(like_date), min(like_date) from Likes inner join News on News.news_id = Likes.news_id group by News.title")
//    List<Object[]> getTopPost();

}
