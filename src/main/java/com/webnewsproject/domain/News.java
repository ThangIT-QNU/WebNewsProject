package com.webnewsproject.domain;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "news")
@Data
public class News implements Serializable, Comparable<News>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private int newsId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "author")
    private String author;

    @Column(name = "date_upload")
    private Date dateUpload;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Transient
    private String imgPath;

    @OneToMany(mappedBy = "news")
    private List<Likes> likes;

    @OneToMany(mappedBy = "news")
    private List<Comments> comments;

    @OneToMany(mappedBy = "news")
    private List<Shares> shares;

    public String getImgPath(){
        if (mainImage == null) return null;
        return "/uploads/PostImageFile/"+mainImage;
    }

    @Override
    public int compareTo(News post) {
        Long dateUpload = this.getDateUpload().getTime();
        return (int) (post.getDateUpload().getTime() - dateUpload);
    }
}
