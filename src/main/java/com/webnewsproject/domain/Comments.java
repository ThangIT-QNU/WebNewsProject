package com.webnewsproject.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name="comments")
public class Comments implements Serializable, Comparable<Comments> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "content")
    private String content;

    @Column(name = "comment_date")
    private Date commentDate;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Override
    public int compareTo(Comments comment) {
        Long dateComment= this.getCommentDate().getTime();
        return (int) (comment.getCommentDate().getTime() - dateComment);
    }
}
