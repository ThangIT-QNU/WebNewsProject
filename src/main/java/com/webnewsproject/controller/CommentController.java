package com.webnewsproject.controller;

import com.webnewsproject.domain.Comments;
import com.webnewsproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @GetMapping("/admin/manage-comment")
    public String manageComment(Model model){
        return listAllComment(1,null,model);
    }

    @GetMapping("/admin/manage-comment/page/{pageNumber}")
    public String listAllComment(@PathVariable("") int pageNumber,
                                 @RequestParam("keyword") String keyword,
                                 Model model){
        if (pageNumber == 0) pageNumber = 1;
        Page<Comments> commentPage = commentService.listAllComment(pageNumber-1, keyword);
        List<Comments> comments = commentPage.getContent();
        int totalPage = commentPage.getTotalPages();
        int totalElement = (int) commentPage.getTotalElements();
        model.addAttribute("currentPage",pageNumber);
        model.addAttribute("comments", comments);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalElement",totalElement);
        model.addAttribute("keyword",keyword);
        return "admin/adminManageComment";
    }

    @GetMapping("/admin/manage-comment/delete/{id}")
    public String delete(@PathVariable("id") int id){
        commentService.deleteById(id);
        return "redirect:/admin/manage-comment";
    }
}
