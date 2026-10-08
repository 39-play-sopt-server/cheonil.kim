package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;

import java.time.LocalDate;
import java.util.List;

public class PostService {
    PostRepository postRepository = new PostRepository();

    public void createPost(String title, PostCategory category, String content){
        postRepository.addPost(title, category, content, LocalDate.now());
    }

    public List<Post> getPostList(){
        return postRepository.getPostList();
    }

    public Post getPost(int id){
        return postRepository.getPost(id)
                .orElseThrow(PostNotFoundException::new);
    }

    public void updatePost(int id, String newTitle, String newContent){
        Post post = getPost(id);
        post.updateTitle(newTitle);
        post.updateContent(newContent);
    }

    public void deletePost(int id) {
        getPost(id); //게시글이 존재하는지 확인용
        postRepository.deletePost(id);
    }
}
