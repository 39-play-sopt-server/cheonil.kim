package org.sopt.domain;

import jakarta.persistence.*;
import org.sopt.code.PostErrorCode;
import org.sopt.exception.InvalidPostException;
import java.time.LocalDate;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private PostCategory category;
    private String content;
    private LocalDate writtenDate;
    private int viewCount = 0;

    protected Post() {}

    private Post(String title, PostCategory category, String content) {
        this.title = title;
        this.category=category;
        this.content = content;
        this.writtenDate=LocalDate.now();
    }

    public static Post create(String title, PostCategory category, String content){
        validate(title, content);
        return new Post(title, category, content);
    }

    public void increaseViewCount(){
        this.viewCount++;
    }

    public Long getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getContent(){
        return this.content;
    }

    public PostCategory getCategory(){
        return this.category;
    }

    public LocalDate getWrittenDate(){
        return this.writtenDate;
    }

    public int getViewCount() { return viewCount; }

    public void update(String title, String content){
        validate(title, content);
        this.title=title;
        this.content=content;
    }

    private static void validate(String title, String content){
        if(title==null || title.isBlank()) throw new InvalidPostException(PostErrorCode.BLANK_INPUT);
        if(content==null || content.isBlank()) throw new InvalidPostException(PostErrorCode.BLANK_INPUT);
    }
}