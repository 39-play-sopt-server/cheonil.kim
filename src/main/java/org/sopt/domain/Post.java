package org.sopt.domain;

import java.time.LocalDate;

public class Post {
    private int id;
    private String title;
    private PostCategory category;
    private String content;
    private LocalDate writtenDate;

    public Post(int id, String title, PostCategory category, String content, LocalDate writtenDate) {
        this.id=id;
        this.title = title;
        this.category=category;
        this.content = content;
        this.writtenDate=writtenDate;
    }

    public int getId(){
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

    public void updateTitle(String title){
        this.title=title;
    }

    public void updateContent(String content){
        this.content=content;
    }
}