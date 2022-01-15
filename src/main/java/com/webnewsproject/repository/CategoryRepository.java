package com.webnewsproject.repository;

import com.webnewsproject.domain.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category,Integer> {
    @Query("select c from Category c where c.categoryId=?1")
    Category findById(int id);

    @Query("select count(c.categoryId) from Category c")
    int countCategories();
}
