package org.sopt.repository;

import org.sopt.domain.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    List<Post> getPostList();
    Optional<Post> getPost(Long id);
    void deletePost(Long id);
}
