package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Repository
public class InMemoryPostRepository implements PostRepository{
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idCount= new AtomicLong(1L);

    @Override
    public Post save(Post post){
        post.assignId(idCount.getAndIncrement());
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public List<Post> getPostList(PostCategory category, int page, int size){
        return filtered(category)
                .skip((long)(page-1)*size)
                .limit(size)
                .toList();
    }

    @Override
    public long count(PostCategory category){
        return filtered(category).count();
    }

    private Stream<Post> filtered(PostCategory category){
        return posts.values().stream()
                .filter(p -> category == null || p.getCategory() == category)
                .sorted(Comparator.comparing(Post::getId));
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
