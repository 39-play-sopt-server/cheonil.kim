package org.sopt.repository;

import org.sopt.domain.Post;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class InMemoryPostRepository implements PostRepository{
    private final Map<Long, Post> posts = new LinkedHashMap<>();
    private long idCount=1L;

    @Override
    public Post save(Post post){
        post.assignId(idCount++);
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public List<Post> getPostList(){
        return List.copyOf(posts.values());
    }

    @Override
    public Optional<Post> getPost(Long id){
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public void deletePost(Long id){
        posts.remove(id);
    }
}
