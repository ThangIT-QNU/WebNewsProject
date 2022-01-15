package com.webnewsproject.repository;

import com.webnewsproject.domain.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface UserRepository extends PagingAndSortingRepository<Users,Integer> {
    @Query("Select u from Users u where u.userId=?1")
    Users findById(int id);

    @Query("select u from Users u where u.username=?1")
    Users findByUsername(String username);

    @Query("select u from Users u where u.email=?1")
    Users getUserByEmail(String email);

    @Query("select u from Users u where u.resetPasswordToken=?1")
    Users getUserByResetPasswordToken(String token);

    @Query("select u from Users u where " +
            "concat(u.username,'',u.fullname,'',u.email,'') " +
            "like %?1%")
    Page<Users> search(String keyword, Pageable pageable);

    @Query("select count(u.userId) from Users u")
    int countUser();
}
