package org.sopt.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.swing.*;

@Entity
@Table(name = "post_like",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"member_id", "post_id"}
                )
        })
public class PostLike extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    protected PostLike(){}

    private PostLike(Member member,  Post post){
        this.member = member;
        this.post = post;
    }

    public static PostLike create(Member member, Post post){
        return new PostLike(member, post);
    }

    public Long getId() { return this.id; }

    public Member getMember() { return this.member; }

    public Post getPost() { return this.post; }
}
