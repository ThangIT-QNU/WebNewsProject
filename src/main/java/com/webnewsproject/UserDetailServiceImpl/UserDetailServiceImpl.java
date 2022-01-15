package com.webnewsproject.UserDetailServiceImpl;

import com.webnewsproject.UserDetailService.MyUserDetail;
import com.webnewsproject.domain.Users;
import com.webnewsproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("Tên người sử dụng không được tìm thấy");
        }

        return new MyUserDetail(user);
    }
}
