package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {
    List<Post> posts = new ArrayList<>();
    private int idCount=1;

    public void addPost(String title, PostCategory category, String content, LocalDate date){
        posts.add(new Post(idCount++, title, category, content, date));
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

    public void deletePost(int index){
        posts.remove(index);
    }
}
