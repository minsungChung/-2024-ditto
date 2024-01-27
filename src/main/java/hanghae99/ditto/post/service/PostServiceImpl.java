package hanghae99.ditto.post.service;

import hanghae99.ditto.global.entity.UsageStatus;
import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.post.domain.Post;
import hanghae99.ditto.post.domain.PostRepository;
import hanghae99.ditto.post.dto.request.PostRequest;
import hanghae99.ditto.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse uploadPost(PostRequest postRequest) {
        Post post = Post.builder()
                .member((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();
        postRepository.save(post);

        return new PostResponse(post);
    }

    public PostResponse getPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 게시글입니다.");
        });
        if (isPostDeleted(post)){
            throw new IllegalArgumentException("삭제된 게시글입니다.");
        }
        return new PostResponse(post);
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest postRequest){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 게시글입니다.");
        });
        if (isPostDeleted(post)){
            throw new IllegalArgumentException("삭제된 게시글입니다.");
        }
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (post.getMember().getId().equals(member.getId())){
            post.updatePost(postRequest.getTitle(), postRequest.getContent());
        } else{
            throw new RuntimeException("권한이 없는 유저입니다.");
        }

        return new PostResponse(post);
    }

    @Transactional
    public PostResponse deletePost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 게시글입니다.");
        });
        if (isPostDeleted(post)){
            throw new IllegalArgumentException("이미 삭제된 게시글입니다.");
        }
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (post.getMember().getId().equals(member.getId())){
            post.deletePost();
        } else{
            throw new RuntimeException("권한이 없는 유저입니다.");
        }
        return new PostResponse(post);
    }

    public boolean isPostDeleted(Post post){
        if (post.getStatus().equals(UsageStatus.DELETED)){
            return true;
        }
        return false;
    }
}
