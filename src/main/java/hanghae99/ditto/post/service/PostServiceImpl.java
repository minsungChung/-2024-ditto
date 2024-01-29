package hanghae99.ditto.post.service;

import hanghae99.ditto.auth.exception.InvalidAccessException;
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
import hanghae99.ditto.post.exception.NoSuchPostException;
import lombok.RequiredArgsConstructor;
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
    public PostResponse uploadPost(Member member, PostRequest postRequest) {
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
    public PostResponse getPost(Member member, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new NoSuchPostException();
        });
        if (isPostDeleted(post)){
            throw new NoSuchPostException();
        }

        // 조회한 사람이 자신이 아닐 때 조회수 1 높임
        if (!member.equals(post.getMember())){
            post.addView();
        }
        return new PostResponse(post);
    }

    @Transactional
    public PostResponse updatePost(Member member, Long postId, PostRequest postRequest){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new NoSuchPostException();
        });
        if (isPostDeleted(post)){
            throw new NoSuchPostException();
        }

        if (member.equals(post.getMember())){
            post.updatePost(postRequest.getTitle(), postRequest.getContent());
        } else{
            throw new InvalidAccessException();
        }

        return new PostResponse(post);
    }

    @Transactional
    public PostResponse deletePost(Member member, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new NoSuchPostException();
        });
        if (isPostDeleted(post)){
            throw new NoSuchPostException();
        }

        if (member.equals(post.getMember())){
            post.deletePost();
        } else{
            throw new InvalidAccessException();
        }
        return new PostResponse(post);
    }

    @Transactional
    public PostLikeResponse pushPostLike(Member member, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new NoSuchPostException();
        });
        if (isPostDeleted(post)){
            throw new NoSuchPostException();
        }
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
                        NewsfeedRequest newsfeedRequest = new NewsfeedRequest(follow.getFromMember().getId(), member.getId(), receiver.getId(), "POSTLIKE", post.getTitle());
                        newsfeedService.createNewsfeed(newsfeedRequest);
                    }
            );
        }

        return new PostLikeResponse(postLike);
    }

    public boolean isPostDeleted(Post post){
        if (post.getStatus().equals(UsageStatus.DELETED)){
            return true;
        }
        return false;
    }
}
