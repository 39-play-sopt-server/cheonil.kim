package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class InMemoryPostRepository implements PostRepository{
    private final Map<Long, Post> posts = new LinkedHashMap<>();
    private long idCount=1L;

    public void addPost(String title, PostCategory category, String content, LocalDate date){
        Post post = new Post(idCount, title, category, content, date);
        posts.put(idCount++, post);
    }

    public List<Post> getPostList(){
        return List.copyOf(posts.values());
    }

    public Optional<Post> getPost(Long id){
        return Optional.ofNullable(posts.get(id));
    }

    public void deletePost(Long id){
        posts.remove(id);
    }
}
