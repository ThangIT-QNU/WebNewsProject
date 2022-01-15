package com.webnewsproject.repository;

import com.webnewsproject.domain.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends PagingAndSortingRepository<News, Integer> {
    @Query("select n from News n where n.newsId=?1")
    News findById(int id);

    @Query("select n from News n join n.category c join n.status s where " +
            "concat(n.title, n.author, c.categoryName, s.statusName) " +
            "like %?1%")
    Page<News> search(String keyword, Pageable pageable);

    @Query("select n from News n join n.category where n.category.categoryId=?1")
    List<News> findAllByCategoryId(int categoryId);

    @Query("select n from News n join n.category where n.status.statusId=?1")
    List<News> findAllByStatusId(int statusId);

    @Query(nativeQuery = true,value = "select * from News n where n.category_id = ?1 order by n.date_upload desc  limit 3")
    List<News> findTopByCategoryId(int categoryId);

    @Query(nativeQuery = true,value = "select * from News n where n.status_id = ?1 order by n.date_upload desc  limit 3")
    List<News> findTopByStatusId(int id);

    @Query("select n from News n join n.category c join n.status s where " +
            "concat(n.title, n.author, c.categoryName, s.statusName) " +
            "like %?1%")
    List<News> userSearch(String keyword);

    @Query("select count(n.newsId) from News n")
    int countNews();

}