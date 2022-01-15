package com.webnewsproject.repository;

import com.webnewsproject.domain.Roles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Roles,Integer> {
    @Query("select r from Roles r where r.roleId=?1")
    Roles findById(int id);

    @Query("select r from Roles r where r.roleName=?1")
    Roles findByName(String rolename);

    @Query("select count(r.roleId) from Roles r")
    int countRoles();
}
