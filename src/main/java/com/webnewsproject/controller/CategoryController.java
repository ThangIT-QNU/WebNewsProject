package com.webnewsproject.controller;

import com.webnewsproject.domain.Category;
import com.webnewsproject.service.CategoryService;
import com.webnewsproject.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/manage-category")
    public String manageCategory(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "admin/adminManageCategory";
    }

    @GetMapping("/admin/add-category")
    public String addCategory(Model model){
        model.addAttribute("category", new Category());
        return "admin/adminAddCategory";
    }

    @PostMapping("/admin/add-category")
    public String doAddCategory(
            @ModelAttribute("category") Category c,
            @RequestParam("img") MultipartFile part,
            @RequestParam("oldImage") String oldImage
    ){
            String filename = StringUtils.cleanPath(part.getOriginalFilename());
            if (filename.equals("")){
                c.setImage(oldImage);
            }else{
                c.setImage(filename);
                String uploadDir = "./uploads/uploadCategory";
                try {
                    UploadService.uploadImage(part,filename,uploadDir);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }

            categoryService.save(c);

            return "redirect:/admin/manage-category";

    }

    @GetMapping("/admin/manage-category/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id, Model model){
        Category c = categoryService.findById(id);
        if (c != null) {
            categoryService.delete(c);
            return "redirect:/admin/manage-category";
        }
        return "error/404";
    }



    @GetMapping("/admin/manage-category/edit/{id}")
    public String editCategory(@PathVariable("id") int id, Model model){
        Category c = categoryService.findById(id);
        if (c != null) {
            model.addAttribute("category",c);
            return "admin/adminAddCategory";
        }
        return "error/404";
    }
}
