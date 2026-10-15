package org.sopt.domain;

import org.sopt.code.PostErrorCode;
import org.sopt.exception.InvalidPostException;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Post {
    private Long id;
    private String title;
    private final PostCategory category;
    private String content;
    private final LocalDate writtenDate;
    private final AtomicInteger viewCount = new AtomicInteger(0);

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

    public void assignId(Long id){
        if(this.id != null){
            throw new IllegalStateException("이미 id가 부여된 게시글입니다.");
        }
        this.id=id;
    }

    public void increaseViewCount(){
        viewCount.incrementAndGet();
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

    public int getViewCount() { return viewCount.get(); }

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