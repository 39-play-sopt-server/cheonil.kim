package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
import org.sopt.exception.PostException;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;

import java.time.LocalDate;
import java.util.List;

public class PostService {
    PostRepository postRepository = new PostRepository();

    public void createPost(String title, PostCategory category, String content){
        postRepository.addPost(title, category, content, LocalDate.now());
    }

    public List<String> getPostTitleList(){
        return postRepository.getPostList().stream().map(Post::getTitle).toList();
    }

    public Post getPost(int index){
        if (index < 0 || index >= postRepository.getTotalPost()) {
            throw new PostNotFoundException();
        } else {
            return postRepository.getPost(index);
        }
    }

    public void updatePost(int index, String newTitle, String newContent){
        Post post = getPost(index);
        post.updateTitle(newTitle);
        post.updateContent(newContent);
    }

    public void deletePost(int index) {
        getPost(index);
        postRepository.deletePost(index);
    }
}
