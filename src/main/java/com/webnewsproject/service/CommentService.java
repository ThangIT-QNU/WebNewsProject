package com.webnewsproject.service;

import com.webnewsproject.domain.Comments;
import com.webnewsproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {
    @Autowired
    CommentRepository repo;

    public Page<Comments> listAllComment(int pageNumber, String keyword){
        Pageable pageable = PageRequest.of(pageNumber,10);
        if (keyword !=null){
            return repo.search(keyword,pageable);
        }
        return repo.findAll(pageable);
    }

    public List<Comments> findAll(){
        Sort sort = Sort.by(Sort.Direction.DESC, "commentDate");
        return repo.findAll(sort);
    }

    public void save(Comments comment){
        repo.save(comment);
    }

    public Comments findComment(int newsId, String username){
        return repo.findComment(newsId, username);
    }

    public List<Comments> findAllByPostId(int id){
        return repo.findAllByPostId(id);
    }

    public int countComment(){
        return repo.countComment();
    }

    public void deleteById(int id){
        repo.deleteById(id);
    }
}
