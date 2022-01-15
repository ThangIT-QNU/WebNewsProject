package com.webnewsproject.service;

import com.webnewsproject.domain.News;
import com.webnewsproject.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository repo;

    public void save(News news){
         repo.save(news);
    }

    public Page<News> findAllNews(int pageNumber, String sortDir, String sortField, String keyword){
        Sort.Direction direction = sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortField);

        Pageable pageable = PageRequest.of(pageNumber,5,sort);
        if (keyword != null){
            return repo.search(keyword,pageable);
        }
        return repo.findAll(pageable);
    }

    public void deletePostById(int id){
        repo.deleteById(id);
    }

    public News findById(int id){
        return repo.findById(id);
    }

    public Page<News> recentlyAddedPost(){
        Sort sort = Sort.by(Sort.Direction.DESC,"dateUpload");
        Pageable pageable = PageRequest.of(0,10,sort);
        return repo.findAll(pageable);
    }

    public List<News> findAllByCategoryId(int id){
        return repo.findAllByCategoryId(id);
    }

    public List<News> findAllByStatusId(int id){
        return repo.findAllByStatusId(id);
    }


    public List<News> findTopByCategoryId(int id){
        return repo.findTopByCategoryId(id);
    }

    public List<News> findTopByStatusId(int id){
        return repo.findTopByStatusId(id);
    }

    public List<News> userSearch(String keyword){

        return repo.userSearch(keyword);
    }

    public int countNews(){
       return repo.countNews();
    }

}
