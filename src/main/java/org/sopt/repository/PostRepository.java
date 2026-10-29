package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Override
    @EntityGraph(attributePaths = "member")
    Page<Post> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "member")
    Page<Post> findByCategory(PostCategory category, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "member")
    Optional<Post> findById(Long id);
}
