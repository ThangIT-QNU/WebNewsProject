package com.webnewsproject.service;

import com.webnewsproject.domain.Status;
import com.webnewsproject.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    private StatusRepository repo;

    public List<Status> findAll(){
        return (List<Status>) repo.findAll();
    }

    public Status findById(int id){
        return repo.findById(id);
    }

    public void delete(Status status){
        repo.delete(status);
    }

    public void save(Status status){
        repo.save(status);
    }

    public int countStatus(){
        return repo.countStatus();
    }
}
