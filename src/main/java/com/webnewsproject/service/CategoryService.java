package com.webnewsproject.service;

import com.webnewsproject.domain.Category;
import com.webnewsproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repo;


    public List<Category> findAll(){
        return (List<Category>) repo.findAll();
    }

    public Category findById(int id){
        return repo.findById(id);
    }

    public void save(Category category){
        repo.save(category);
    }

    public void delete(Category category){
        repo.delete(category);
    }

    public int countCategory(){
        return repo.countCategories();
    }
}
