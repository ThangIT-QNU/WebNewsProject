package com.webnewsproject.repository;

import com.webnewsproject.domain.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends PagingAndSortingRepository<Status, Integer> {
    @Query("select st from Status st where st.statusId=?1")
    Status findById(int id);

    @Query("select count (s.statusId) from Status s")
    int countStatus();
}
