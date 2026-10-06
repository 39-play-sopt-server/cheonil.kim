package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;

import java.util.List;

public class PostService {
    PostRepository postRepository = new PostRepository();

    public void createPost(String title, String content){
        postRepository.addPost(new Post(title, content));
    }

    public List<String> getPostTitleList(){
        return postRepository.getPostList().stream().map(Post::getTitle).toList();
    }

    public boolean isEmpty(){
        return postRepository.isEmpty();
    }

    public Post getPost(int index){
        if (index < 0 || index >= postRepository.getTotalPost()) {
            return null;
        } else {
            return postRepository.getPost(index);
        }
    }

    public boolean isPostExist(int index){
        return index >= 0 && index < postRepository.getTotalPost();
    }

    public void updatePost(int index, String newTitle, String newContent){
        postRepository.updatePost(index, newTitle, newContent);
    }

    public void deletePost(int index) {
        postRepository.deletePost(index);
    }
}
