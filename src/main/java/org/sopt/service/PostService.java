package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
import org.sopt.dto.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;

import java.time.LocalDate;
import java.util.List;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository repository) {
        this.postRepository = repository;
    }

    public void createPost(String title, PostCategory category, String content){
        postRepository.addPost(title, category, content, LocalDate.now());
    }

    public List<PostResponse> getPostList(){
        return postRepository.getPostList().stream()
                .map(PostResponse::from)
                .toList();
    }

    //service 내부에서만 사용, post 엔티티를 그대로 반환
    private Post findPostById(int id){
        return postRepository.getPost(id)
                .orElseThrow(PostNotFoundException::new);
    }

    //외부로 내보낼때 사용, DTO로 반환
    public PostResponse getPost(int id){
        return PostResponse.from(findPostById(id));
    }

    public void updatePost(int id, String newTitle, String newContent){
        Post post = findPostById(id);
        post.updateTitle(newTitle);
        post.updateContent(newContent);
    }

    public void deletePost(int id) {
        findPostById(id); //게시글이 존재하는지 확인용
        postRepository.deletePost(id);
    }
}
