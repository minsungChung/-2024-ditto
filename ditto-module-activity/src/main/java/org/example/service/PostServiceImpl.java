package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.controller.api.NewsfeedApi;
import org.example.global.dto.NewsfeedPostRequest;
import org.example.dto.response.PostResponse;
import org.example.global.dto.PostDto;
import org.example.global.exception.InvalidAccessException;
import org.example.domain.*;
import org.example.global.dto.NewsfeedRequest;
import org.example.global.entity.UsageStatus;
import org.example.domain.FollowRepository;
import org.example.dto.request.PostRequest;
import org.example.dto.response.PostLikeResponse;
import org.example.global.exception.NoSuchPostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final FollowRepository followRepository;
    private final NewsfeedApi newsfeedApi;

    @Transactional
    public PostResponse uploadPost(Long memberId, PostRequest postRequest) {
        Post post = Post.builder()
                .memberId(memberId)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();
        Post save = postRepository.save(post);

        followRepository.findAllByToMemberId(memberId).forEach(
                follow -> {
                    NewsfeedPostRequest newsfeedPostRequest = NewsfeedPostRequest.builder()
                            .feedMemberId(follow.getFromMemberId())
                            .postId(post.getId())
                            .writerMemberId(post.getMemberId()).build();
                    newsfeedApi.createNewsfeedPost(newsfeedPostRequest);
                }
        );

        return new PostResponse(post);
    }

    @Transactional
    public PostResponse getPost(Long memberId, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new NoSuchPostException();
        });
        if (isPostDeleted(post)){
            throw new NoSuchPostException();
        }

        // 조회한 사람이 자신이 아닐 때 조회수 1 높임
        if (!memberId.equals(post.getMemberId())){
            post.addView();
        }
        return new PostResponse(post);
    }

    @Transactional
    public PostResponse updatePost(Long memberId, Long postId, PostRequest postRequest){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new NoSuchPostException();
        });
        if (isPostDeleted(post)){
            throw new NoSuchPostException();
        }

        if (memberId.equals(post.getMemberId())){
            post.updatePost(postRequest.getTitle(), postRequest.getContent());
        } else{
            throw new InvalidAccessException();
        }

        return new PostResponse(post);
    }

    @Transactional
    public PostResponse deletePost(Long memberId, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new NoSuchPostException();
        });
        if (isPostDeleted(post)){
            throw new NoSuchPostException();
        }

        if (memberId.equals(post.getMemberId())){
            post.deletePost();
        } else{
            throw new InvalidAccessException();
        }
        return new PostResponse(post);
    }

    @Transactional
    public PostLikeResponse pushPostLike(Long memberId, Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new NoSuchPostException();
        });
        if (isPostDeleted(post)){
            throw new NoSuchPostException();
        }
        PostLike postLike = postLikeRepository.findByMemberIdAndPostId(memberId, postId).orElse(null);
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
                    .memberId(memberId)
                    .post(post).build();
            postLikeRepository.save(postLike);
        }

        if (flag){
            Long receiverId = post.getMemberId();
            followRepository.findAllByToMemberId(memberId).forEach(
                    follow -> {
                        NewsfeedRequest newsfeedRequest = new NewsfeedRequest(follow.getFromMemberId(), memberId, receiverId, "POSTLIKE", post.getTitle());
                        newsfeedApi.createNewsfeed(newsfeedRequest);
                    }
            );
        }

        return new PostLikeResponse(postLike);
    }

    public PostDto getOnePost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new NoSuchPostException();
        });
        return PostDto.builder()
                .postId(post.getId())
                .writerId(post.getMemberId())
                .title(post.getTitle())
                .content(post.getContent())
                .createDate(post.getCreateDate())
                .likes(post.getLikes())
                .views(post.getViews()).build();
    }

    public boolean isPostDeleted(Post post){
        if (post.getStatus().equals(UsageStatus.DELETED)){
            return true;
        }
        return false;
    }
}
