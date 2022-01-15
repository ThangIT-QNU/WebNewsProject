package com.webnewsproject.controller;

import com.webnewsproject.domain.*;
import com.webnewsproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private NewsService newsService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/")
    public String render(Model model) {
        List<Status> statuses = statusService.findAll();
        List<News> featuredNews = newsService.findAllByStatusId(3);
        List<News> focusNews = newsService.findTopByStatusId(5);
        List<News> hotNews = newsService.findTopByStatusId(1);
        List<News> favoriteNews = newsService.findTopByStatusId(4);
        List<News> eventNews = newsService.findAllByCategoryId(12);

        model.addAttribute("status", statusService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("recentlyPosts", newsService.recentlyAddedPost());
        model.addAttribute("featuredNews", featuredNews);
        model.addAttribute("hotNews", hotNews);
        model.addAttribute("favoriteNews", favoriteNews);
        model.addAttribute("focusNews", focusNews);
        model.addAttribute("statuses", statuses);
        model.addAttribute("eventNews", eventNews);

        return "sites/home";
    }

    @GetMapping("/post/{id}")
    public String postDetail(
            @PathVariable("id") int id,
            Authentication authentication,
            Model model
    ) {
        News post = newsService.findById(id);
        List<News> postOfCategory = post.getCategory().getNews();
        postOfCategory.remove(post);
        Collections.sort(postOfCategory);

        List<News> recomments = new ArrayList<>();

        if (postOfCategory.size() > 10) {
            for (int i = 0; i < 10; i++) {
                recomments.add(postOfCategory.get(i));
                model.addAttribute("recomments", recomments);
            }
        } else {
            model.addAttribute("recomments", postOfCategory);
        }

        if (authentication != null) {
            Likes like = likeService.findLike(id, authentication.getName());
            Boolean isLiked = like != null ? true : false;
            model.addAttribute("isLiked", isLiked);
        }

        List<Comments> comments =  commentService.findAllByPostId(id);
        Collections.sort(comments);
        model.addAttribute("allComment",comments);
        model.addAttribute("likeNumber", likeService.likeNumber(id));
        model.addAttribute("post", post);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("statuses", statusService.findAll());
        return "sites/postdetail";
    }

    @GetMapping("/post/{id}/like")
    @ResponseBody
    public int like(
            @PathVariable("id") int id,
            @RequestParam("status") String status,
            Authentication authentication,
            HttpServletResponse response
    ) throws IOException {
        Likes like = new Likes();
        like.setUser(userService.findByUsername(authentication.getName()));
        like.setNews(newsService.findById(id));
        like.setLikeDate(new Date());

        if (status.equals("nolike")) {
            likeService.save(like);
        }
        if (status.equals("like")) {
            Likes likeDelete = likeService.findLike(id, authentication.getName());
            likeService.delete(likeDelete);
        }

        return likeService.likeNumber(id);
    }

    @GetMapping("/post/{id}/comment")
    @ResponseBody
    public String doComment(@PathVariable("id") int id, @RequestParam("content") String comment, Authentication authentication) {
        Users userCmt = userService.findByUsername(authentication.getName());
        Comments cmt = new Comments();
        cmt.setCommentDate(new Date());
        cmt.setNews(newsService.findById(id));
        cmt.setUser(userCmt);
        cmt.setContent(comment);
        commentService.save(cmt);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(new Date());
        String avartar = userCmt.getAvartar();
        String fullname = userCmt.getFullname();

        return  "<div class=\"comment\"> \n"+
                "<img alt=\"\" src=\"/webnews/uploads/AvarterUserUploadFolder/"+avartar+" \">\n" +
                "<div class=\"comment-content\">\n" +
                "<h6>"+fullname+"</h6>\n" +
                "<span>"+ comment +"</span>\n" +
                "<br>\n" +
                "<small id=\"justCommentTime\">"+ strDate +"</small>\n" +
                "</div>\n" +
                "</div>";
    }

    @GetMapping("/category/{id}")
    public String listPostCategory(@PathVariable("id") int id, Model model){
        Category category = categoryService.findById(id);
        List<News> listPosts = category.getNews();
        Collections.sort(listPosts);
        if (listPosts.size() > 0){
            News firstPost = listPosts.get(0);
            listPosts.remove(firstPost);
            model.addAttribute("firstPost", firstPost);
        }


        model.addAttribute("title", category.getCategoryName());
        model.addAttribute("listPosts", listPosts);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("statuses", statusService.findAll());
        return "sites/categorydetail";
    }

    @GetMapping("/status/{id}")
    public String listPostStatus(@PathVariable("id") int id, Model model){
        Status status = statusService.findById(id);
        List<News> listPosts = status.getNews();
        Collections.sort(listPosts);
        if (listPosts.size() >= 0){
            News firstPost = listPosts.get(0);
            listPosts.remove(firstPost);
            model.addAttribute("firstPost", firstPost);
        }


        model.addAttribute("title", status.getStatusName());
        model.addAttribute("listPosts", listPosts);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("statuses", statusService.findAll());
        return "sites/categorydetail";
    }

    @GetMapping("/search")
    @ResponseBody
    public Map<Integer,String> getKeyword(@RequestParam("keyword") String keyword){
        Map<Integer,String> listObjectNews = new HashMap<>();

        if (keyword != null && !keyword.equals("")){
            List<News> newsList = newsService.userSearch(keyword);
            if (newsList.size() > 0){
                newsList.forEach(post -> {
                    listObjectNews.put(post.getNewsId(),post.getTitle());
                });
            }
            return listObjectNews;
        }

        return Collections.emptyMap();
    }


    @GetMapping("/search-bar")
    public String userSearch(@RequestParam("search-bar") String keyword, Model model){
        List<News> listPosts = newsService.userSearch(keyword);
        Collections.sort(listPosts);
        System.out.println(listPosts.size());
        if (listPosts.size() > 0){
            News firstPost = listPosts.get(0);
            listPosts.remove(firstPost);
            model.addAttribute("firstPost", firstPost);
        }


        model.addAttribute("title", "kết quả tìm kiếm cho: "+keyword);
        model.addAttribute("listPosts", listPosts);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("statuses", statusService.findAll());


        return "sites/categorydetail";
    }
}
