package org.example.service;

import org.example.aop.Retry;
import org.example.controller.api.MemberApi;
import org.example.controller.api.NewsfeedApi;
import org.example.global.dto.MemberDto;
import org.example.global.exception.InvalidAccessException;
import org.example.domain.*;
import org.example.dto.request.CommentRequest;
import org.example.global.dto.NewsfeedRequest;
import org.example.dto.response.CommentLikeResponse;
import org.example.dto.response.CommentResponse;
import org.example.global.exception.NoSuchCommentException;
import org.example.global.exception.NoSuchPostException;
import org.example.global.entity.UsageStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final FollowRepository followRepository;
    private final NewsfeedApi newsfeedApi;
    private final MemberApi memberApi;

    @Transactional
    public CommentResponse uploadComment(Long memberId, Long postId, CommentRequest commentRequest) {
        Post post = checkPostAvailability(postId);
        Comment comment = Comment.builder()
                .postId(postId)
                .memberId(memberId)
                .content(commentRequest.getContent()).build();
        commentRepository.save(comment);

        Long receiverId = post.getMemberId();
        followRepository.findAllByToMemberId(memberId).forEach(
                follow -> {
                    NewsfeedRequest newsfeedRequest = new NewsfeedRequest(follow.getFromMemberId(), memberId, receiverId, "COMMENT", post.getTitle());
                    newsfeedApi.createNewsfeed(newsfeedRequest);
                }
        );
        MemberDto member = memberApi.findById(memberId).getResult();
        return new CommentResponse(member.getMemberName(), comment);
    }

    public List<CommentResponse> getComments(Long postId) {
        checkPostAvailability(postId);
        return commentRepository.findAllByPostId(postId).stream().map(
                comment -> {
                    MemberDto member = memberApi.findById(comment.getMemberId()).getResult();
                    return new CommentResponse(member.getMemberName(), comment);
                }
        ).collect(Collectors.toList());

    }

    @Transactional
    public CommentResponse updateComment(Long memberId, Long postId, Long commentId, CommentRequest commentRequest) {
        checkPostAvailability(postId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new NoSuchCommentException();
        });
        if (memberId.equals(comment.getMemberId())){
            if (!isCommentDeleted(comment)) {
                comment.updateContent(commentRequest.getContent());
            } else {
                throw new NoSuchCommentException();
            }
        } else{
            throw new InvalidAccessException();
        }
        MemberDto member = memberApi.findById(comment.getMemberId()).getResult();
        return new CommentResponse(member.getMemberName(), comment);
    }

    @Transactional
    public CommentResponse deleteComment(Long memberId,Long postId, Long commentId) {
        checkPostAvailability(postId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new NoSuchCommentException();
        });
        if (memberId.equals(comment.getMemberId())){
            if (!isCommentDeleted(comment)){
                comment.deleteComment();
            } else {
                throw new NoSuchCommentException();
            }
        }else {
            throw new InvalidAccessException();
        }
        MemberDto member = memberApi.findById(comment.getMemberId()).getResult();
        return new CommentResponse(member.getMemberName(), comment);
    }

    @Transactional
    @Retry
    public CommentLikeResponse pushCommentLike(Long memberId, Long postId, Long commentId){
        Post post = checkPostAvailability(postId);
        Comment comment = commentRepository.findByIdWithLock(commentId).orElseThrow(() -> {
            throw new NoSuchCommentException();
        });
        if (isCommentDeleted(comment)){
            throw new NoSuchCommentException();
        }
        CommentLike commentLike = commentLikeRepository.findByMemberIdAndCommentId(memberId, commentId).orElse(null);

        boolean flag = true;
        if (commentLike != null){
            if (commentLike.getStatus().equals(UsageStatus.ACTIVE)){
                commentLike.deleteCommentLike();
                comment.subLike();
                flag = false;
            } else {
                commentLike.pushCommentLike();
                comment.addLike();
            }
        } else {
            commentLike = CommentLike.builder()
                    .memberId(memberId)
                    .commentId(commentId).build();
            commentLikeRepository.save(commentLike);
            comment.addLike();
        }

        if (flag){
            followRepository.findAllByToMemberId(memberId).forEach(
                    follow -> {
                        NewsfeedRequest newsfeedRequest = new NewsfeedRequest(follow.getFromMemberId(), memberId, post.getMemberId(), "COMMENTLIKE", null);
                        newsfeedApi.createNewsfeed(newsfeedRequest);
                    }
            );
        }
        return new CommentLikeResponse(commentLike);
    }

    public boolean isCommentDeleted(Comment comment){
        if (comment.getStatus().equals(UsageStatus.DELETED)){
            return true;
        }
        return false;
    }

    public Post checkPostAvailability(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new NoSuchPostException();
        });
        if (post.getStatus().equals(UsageStatus.DELETED)){
            throw new NoSuchPostException();
        }

        return post;
    }
}
