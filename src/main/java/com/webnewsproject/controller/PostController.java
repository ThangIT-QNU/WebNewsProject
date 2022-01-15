package com.webnewsproject.controller;

import com.webnewsproject.domain.Category;
import com.webnewsproject.domain.News;
import com.webnewsproject.domain.Status;
import com.webnewsproject.service.CategoryService;
import com.webnewsproject.service.NewsService;
import com.webnewsproject.service.StatusService;
import com.webnewsproject.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private UploadService uploadService;

    @GetMapping("/admin/view-post-detail/{id}")
    public String viewPostDetail(@PathVariable("id") int id, Model model){
        String content = newsService.findById(id).getContent();
        model.addAttribute("content",content);
        return "admin/viewPostModel";
    }

    @GetMapping("/admin/manage-post")
    public String managePost(Model model){
        return listAllNews(1,"asc",null,null,model);
    }

    @GetMapping("/admin/manage-post/page/{pageNumber}")
    public String listAllNews(
            @PathVariable("pageNumber") int pageNumber,
            @RequestParam("sortDir") String sortDir,
            @RequestParam("sortField") String sortField,
            @RequestParam("keyword") String keyword,
            Model model){
        if (sortDir == null) sortDir="asc";
        if (sortField == null) sortField = "newsId";
        if (pageNumber < 0) pageNumber =1;

        String reverseDir = sortDir.equals("asc") ? "dsc" : "asc";

        Page<News> page = newsService.findAllNews(pageNumber-1,sortDir,sortField,keyword);

        List<News> news = page.getContent();
        int totalPage = page.getTotalPages();
        if (totalPage ==0) totalPage =1;
        int totalElement = (int) page.getTotalElements();

        model.addAttribute("news",news);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("totalElement",totalElement);
        model.addAttribute("currentPage",pageNumber);

        model.addAttribute("currentDir",sortDir);
        model.addAttribute("currentField",sortField);
        model.addAttribute("reverseDir",reverseDir);
        model.addAttribute("keyword",keyword);


        return "admin/adminManagePost";
    }


    @GetMapping("/admin/add-post")
    public String adminAddPost(Model model){
        News news = new News();
        Category c = new Category();
        Status s = new Status();

        news.setCategory(c);
        news.setStatus(s);
        model.addAttribute("news",news);
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("status", statusService.findAll());
        return "admin/adminAddPost";
    }

    @PostMapping("/admin/add-post")
    public String adminAddPost(
            @ModelAttribute("news") News news,
            @RequestParam("categoryId") int categoryId,
            @RequestParam("statusId") int statusId,
            @RequestParam("image") MultipartFile part,
            @RequestParam("content") String content,
            @RequestParam("oldImagePost") String oldImagePost,
            Model model

    ){
        news.setDateUpload(new Date());
        Category c = categoryService.findById(categoryId);
        Status st = statusService.findById(statusId);

        news.setCategory(c);
        news.setStatus(st);
        news.setContent(content);


        String filename = StringUtils.cleanPath(part.getOriginalFilename());
        if (filename == ""){
            news.setMainImage(oldImagePost);
        }else{
            news.setMainImage(filename);
            String uploadDir = "./uploads/PostImageFile/";

            try{
                uploadService.uploadImage(part,filename,uploadDir);
            }catch(Exception e){
                model.addAttribute("error","Could not upload image");
            }
        }

        newsService.save(news);
        return "redirect:/admin/manage-post";
    }

    @GetMapping("/admin/manage-post/delete/{id}")
    public String deletePost(@PathVariable("id") int id){
        newsService.deletePostById(id);
        return "redirect:/admin/manage-post";
    }

    @GetMapping("/admin/manage-post/edit/{id}")
    public String editPost(@PathVariable("id") int id, Model model){
        News news = newsService.findById(id);
        model.addAttribute("news",news);
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("status",statusService.findAll());

        return "admin/adminAddPost";
    }

}
