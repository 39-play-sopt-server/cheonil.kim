package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
    void addPost(String title, PostCategory category, String content, LocalDate date);
    List<Post> getPostList();
    Optional<Post> getPost(int id);
    void deletePost(int id);
}
