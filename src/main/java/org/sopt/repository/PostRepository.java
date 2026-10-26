package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCategory(PostCategory category, Pageable pageable);
}
