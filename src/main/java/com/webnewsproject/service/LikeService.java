package com.webnewsproject.service;

import com.webnewsproject.domain.Likes;
import com.webnewsproject.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LikeService {

    @Autowired
    private LikeRepository repo;

    public void save(Likes like){
        repo.save(like);
    }


    public int likeNumber(int newsId){
        return repo.likeNumber(newsId);
    }

    public Likes findLike(int newsId, String username){
        return repo.findLike(newsId,username);
    }

    public void delete(Likes like){
        repo.delete(like);
    }

//    public List<Object[]> getTopPost(){
//        return repo.getTopPost();
//    }
}
