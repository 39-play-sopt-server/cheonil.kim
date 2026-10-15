package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    List<Post> getPostList(PostCategory category, int page, int size);
    long count(PostCategory category);
    Optional<Post> getPost(Long id);
    void deletePost(Long id);
}
