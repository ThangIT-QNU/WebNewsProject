package com.webnewsproject.service;

import com.webnewsproject.CustomerException.UserNotFoundException;
import com.webnewsproject.domain.Users;
import com.webnewsproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public Users findById(int id) {
        return repo.findById(id);
    }

    public Users findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public Users findByEmail(String email){
        return repo.getUserByEmail(email);
    }

    public Users findByResetToken(String token) {
        return repo.getUserByResetPasswordToken(token);
    }

    public void save(Users user) {
        repo.save(user);
    }

    public void deleteById(int id) {
        repo.deleteById(id);
    }

    public List<Users> findAll() {
        return (List<Users>) repo.findAll();
    }

    public Page<Users> findAllAndPaging(int pageNumber, String sortDir, String sortField, String keyword) {
        if (sortDir == null) sortDir = "asc";
        if (sortField == null) sortField = "fullname";

        Sort.Direction direction = sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(pageNumber, 5, sort);

        if (keyword != null) {
            return repo.search(keyword, pageable);
        }
        return repo.findAll(pageable);
    }

    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        Users user = repo.getUserByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            repo.save(user);
        } else {
            throw new UserNotFoundException("Không thể tìm thấy bất kỳ người dùng nào từ email: " + email);
        }
    }

    public Users findUserByResetPasswordToken(String token){
        return repo.getUserByResetPasswordToken(token);
    }

    public void changePassword(Users user, String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncode = encoder.encode(password);

        user.setPassword(passwordEncode);
        user.setResetPasswordToken(null);

        repo.save(user);
    }

    public int countUser(){
        return repo.countUser();
    }
}
