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

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.USER;

    protected Member() {}

    private Member(String email, String name, String password){
        this.name=name;
        this.email=email;
        this.password=password;
    }

    public static Member create(String email, String name, String password){
        return new Member(email,name, password);
    }

    public Long getId() {return this.id;}

    public String getEmail() {return this.email;}

    public String getName() {return this.name;}

    public String getPassword() {return this.password;}

    public Role getRole() {return this.role;}
}
