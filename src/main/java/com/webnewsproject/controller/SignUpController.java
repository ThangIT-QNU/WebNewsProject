package com.webnewsproject.controller;

import com.webnewsproject.domain.Roles;
import com.webnewsproject.domain.Users;
import com.webnewsproject.service.RolesService;
import com.webnewsproject.service.UploadService;
import com.webnewsproject.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Controller
public class SignUpController {
    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("user",new Users());
        return "signupform";
    }

    @PostMapping("/signup")
    public String doSignup(@ModelAttribute("user") Users user, Model model) throws IOException {

        if (userService.findByEmail(user.getEmail()) != null){
            model.addAttribute("emailErr","Email existed!");
            return "signupform";
        }

        if (userService.findByUsername(user.getUsername()) != null){
            model.addAttribute("usernameErr","UserName existed!");
            return "signupform";
        }

        //set default role
        Set<Roles> rolesSet = new HashSet<>();
        rolesSet.add(rolesService.findByName("ROLE_USER"));
        user.setRoles(rolesSet);

        //enable account
        user.setEnable(true);

        //encode password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncode = encoder.encode(user.getPassword());
        user.setPassword(passwordEncode);

        //handle upload default avatar
        File file = new File("src/default/usericon.png");
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "image/png", IOUtils.toByteArray(inputStream));
        String filename = multipartFile.getOriginalFilename();
        String uploadDir = "./uploads/AvarterUserUploadFolder";
        UploadService.uploadImage(multipartFile,filename,uploadDir);
        user.setAvartar(filename);

        //create new account
        userService.save(user);
        model.addAttribute("message","Create new account successfully");
        return "login";
    }
}
