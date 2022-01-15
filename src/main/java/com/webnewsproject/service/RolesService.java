package com.webnewsproject.service;

import com.webnewsproject.domain.Roles;
import com.webnewsproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {
    @Autowired
    private RoleRepository repo;

    public List<Roles> findAll(){
        return (List<Roles>) repo.findAll();
    }

    public Roles findById(int id){
        return repo.findById(id);
    }

    public Roles findByName(String rolename){
        return repo.findByName(rolename);
    }

    public void save(Roles role){
        repo.save(role);
    }

    public void delete(Roles role){
        repo.delete(role);
    }

    public int countRoles(){
        return repo.countRoles();
    }
}
