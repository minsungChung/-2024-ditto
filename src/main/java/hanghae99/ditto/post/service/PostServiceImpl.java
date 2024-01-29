package hanghae99.ditto.post.service;

import hanghae99.ditto.global.entity.UsageStatus;
import hanghae99.ditto.member.domain.FollowRepository;
import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.newsfeed.dto.request.NewsfeedRequest;
import hanghae99.ditto.newsfeed.service.NewsfeedService;
import hanghae99.ditto.post.domain.Post;
import hanghae99.ditto.post.domain.PostLike;
import hanghae99.ditto.post.domain.PostLikeRepository;
import hanghae99.ditto.post.domain.PostRepository;
import hanghae99.ditto.post.dto.request.PostRequest;
import hanghae99.ditto.post.dto.response.PostLikeResponse;
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
    private final PostLikeRepository postLikeRepository;
    private final FollowRepository followRepository;
    private final NewsfeedService newsfeedService;

    @Transactional
    public PostResponse uploadPost(PostRequest postRequest) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = Post.builder()
                .member(member)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();
        postRepository.save(post);

        followRepository.findAllByToMemberId(member.getId()).forEach(
                follow -> {
                    newsfeedService.createPostNewsfeed(follow.getFromMember(), post);
                }
        );

        return new PostResponse(post);
    }

    @Transactional
    public PostResponse getPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 게시글입니다.");
        });
        if (isPostDeleted(post)){
            throw new IllegalArgumentException("삭제된 게시글입니다.");
        }

        // 조회한 사람이 자신이 아닐 때 조회수 1 높임
        if (!checkAuthenticated(post.getMember().getId())){
            post.addView();
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

        if (checkAuthenticated(post.getMember().getId())){
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

        if (checkAuthenticated(post.getMember().getId())){
            post.deletePost();
        } else{
            throw new RuntimeException("권한이 없는 유저입니다.");
        }
        return new PostResponse(post);
    }

    @Transactional
    public PostLikeResponse pushPostLike(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지않은 게시글입니다.");
        });
        if (isPostDeleted(post)){
            throw new IllegalArgumentException("삭제된 게시글에는 좋아요를 누를 수 없습니다.");
        }
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostLike postLike = postLikeRepository.findByMemberIdAndPostId(member.getId(), postId).orElse(null);
        boolean flag = true;

        if (postLike != null){
            if (postLike.getStatus().equals(UsageStatus.ACTIVE)){
                postLike.deletePostLike();
                flag = false;
            } else {
                postLike.pushPostLike();
            }
        } else {
            postLike = PostLike.builder()
                    .member(member)
                    .post(post).build();
            postLikeRepository.save(postLike);
        }

        if (flag){
            Member receiver = post.getMember();
            followRepository.findAllByToMemberId(member.getId()).forEach(
                    follow -> {
                        NewsfeedRequest newsfeedRequest = new NewsfeedRequest(follow.getFromMember().getId(), member.getId(), receiver.getId(), "POSTLIKE");
                        newsfeedService.createNewsfeed(newsfeedRequest);
                    }
            );
        }

        return new PostLikeResponse(postLike);
    }

    public boolean checkAuthenticated(Long memberId){
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (member.getId().equals(memberId)){
            return true;
        }
        return false;
    }

    public boolean isPostDeleted(Post post){
        if (post.getStatus().equals(UsageStatus.DELETED)){
            return true;
        }
        return false;
    }
}
