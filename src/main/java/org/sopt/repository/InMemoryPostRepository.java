package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;

import java.time.LocalDate;
import java.util.*;

public class InMemoryPostRepository implements PostRepository{
    private final Map<Integer, Post> posts = new LinkedHashMap<>();
    private int idCount=1;

    public void addPost(String title, PostCategory category, String content, LocalDate date){
        Post post = new Post(idCount, title, category, content, date);
        posts.put(idCount++, post);
    }

    public List<Post> getPostList(){
        return List.copyOf(posts.values());
    }

    public Optional<Post> getPost(int id){
        return Optional.ofNullable(posts.get(id));
    }

    public void deletePost(int id){
        posts.remove(id);
    }
}
