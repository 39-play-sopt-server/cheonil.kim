package org.sopt.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    protected Member() {}

    private Member(String email, String name){
        this.name=name;
        this.email=email;
    }

    public static Member create(String email, String name){
        return new Member(email,name);
    }

    public Long getId() {return this.id;}

    public String getEmail() {return this.email;}

    public String getName() {return this.name;}
}
