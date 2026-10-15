package org.sopt.service;

import org.sopt.code.PostErrorCode;
import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
import org.sopt.dto.request.PostCreateRequest;
import org.sopt.dto.request.PostUpdateRequest;
import org.sopt.dto.response.PostListResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.InvalidPostException;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository repository) {
        this.postRepository = repository;
    }

    public void createPost(PostCreateRequest request){
        PostCategory category = PostCategory.from(request.category());
        Post post = Post.create(request.title(), category, request.content());
        postRepository.save(post);
    }

    public PostListResponse getPostList(int page, int size, String category){
        if(page<1 || size<1 || size>100){
            throw new InvalidPostException(PostErrorCode.INVALID_PAGE);
        }
        PostCategory postCategory = (category==null) ? null : PostCategory.from(category);

        List<PostResponse> posts = postRepository.getPostList(postCategory, page, size).stream()
                .map(PostResponse::from)
                .toList();

        long total = postRepository.count(postCategory);
        int totalPage = (int) Math.ceil((double)total/size);

        return new PostListResponse(posts, page, size, total, totalPage);
    }

    //service 내부에서만 사용, post 엔티티를 그대로 반환
    private Post findPostById(Long id){
        return postRepository.getPost(id)
                .orElseThrow(PostNotFoundException::new);
    }

    //외부로 내보낼때 사용, DTO로 반환
    public PostResponse getPost(Long id){
        Post post = findPostById(id);
        post.increaseViewCount();;
        return PostResponse.from(findPostById(id));
    }

    public void updatePost(Long id, PostUpdateRequest request){
        Post post = findPostById(id);
        post.update(request.title(), request.content());
    }

    public void deletePost(Long id) {
        findPostById(id); //게시글이 존재하는지 확인용
        postRepository.deletePost(id);
    }
}
