package org.sopt.domain;

import jakarta.persistence.*;
import org.sopt.code.PostErrorCode;
import org.sopt.exception.InvalidPostException;

@Entity
@Table(name = "post")
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private PostCategory category;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    protected Post() {}

    private Post(String title, PostCategory category, String content, Member member) {
        this.title = title;
        this.category=category;
        this.content = content;
        this.member = member;
    }

    public static Post create(String title, PostCategory category, String content, Member member){
        validate(title, content);
        if(category==null) throw new InvalidPostException(PostErrorCode.INVALID_CATEGORY);
        if(member==null) throw new InvalidPostException(PostErrorCode.WRITER_REQUIRED);
        return new Post(title, category, content, member);
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

    public int getViewCount() { return this.viewCount; }

    public int getLikeCount() { return this.likeCount; }

    public Member getMember() { return this.member; }

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