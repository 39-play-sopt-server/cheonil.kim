package org.sopt.repository;

import org.sopt.domain.Post;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {
    List<Post> posts = new ArrayList<>();

    public void addPost(Post post){
        posts.add(post);
    }

    public List<Post> getPostList(){
        return posts;
    }

    public boolean isEmpty(){
        return posts.isEmpty();
    }

    public int getTotalPost(){
        return posts.size();
    }

    public Post getPost(int index){
        return posts.get(index);
    }

    public void updatePost(int index, String newTitle, String newContent){
        Post post = posts.get(index);
        post.updateTitle(newTitle);
        post.updateContent(newContent);
    }

    public void deletePost(int index){
        posts.remove(index);
    }
}
