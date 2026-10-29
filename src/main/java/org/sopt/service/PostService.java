package org.sopt.service;

import org.sopt.code.PostErrorCode;
import org.sopt.domain.Member;
import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
import org.sopt.dto.request.PostCreateRequest;
import org.sopt.dto.request.PostUpdateRequest;
import org.sopt.dto.response.PostListResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.InvalidPostException;
import org.sopt.exception.MemberNotFoundException;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.MemberRepository;
import org.sopt.repository.PostRepository;
import org.sopt.repository.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createPost(PostCreateRequest request){
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(MemberNotFoundException::new);
        PostCategory category = PostCategory.from(request.category());
        Post post = Post.create(request.title(), category, request.content(), member);
        postRepository.save(post);
    }

    public PostListResponse getPostList(int page, int size, String category, String title, Long memberId){
        if(page<1 || size<1 || size>100){
            throw new InvalidPostException(PostErrorCode.INVALID_PAGE);
        }
        PostCategory postCategory = (category==null) ? null : PostCategory.from(category);
        Pageable pageable = PageRequest.of(page-1, size);

        PostSearchCondition condition = new PostSearchCondition(title, postCategory, memberId);
        Page<Post> result = postRepository.search(condition, pageable);

        List<PostResponse> posts = result.getContent().stream()
                .map(PostResponse::from)
                .toList();

        return new PostListResponse(posts, page, size, result.getTotalElements(), result.getTotalPages());
    }

    //service 내부에서만 사용, post 엔티티를 그대로 반환
    private Post findPostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    //외부로 내보낼때 사용, DTO로 반환
    @Transactional
    public PostResponse getPost(Long id){
        Post post = findPostById(id);
        post.increaseViewCount();
        return PostResponse.from(post);
    }

    @Transactional
    public void updatePost(Long id, PostUpdateRequest request){
        Post post = findPostById(id);
        post.update(request.title(), request.content());
    }

    @Transactional
    public void deletePost(Long id) {
        findPostById(id); //게시글이 존재하는지 확인용
        postRepository.deleteById(id);
    }
}
