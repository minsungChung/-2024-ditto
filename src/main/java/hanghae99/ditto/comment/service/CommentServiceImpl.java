package hanghae99.ditto.comment.service;

import hanghae99.ditto.comment.domain.Comment;
import hanghae99.ditto.comment.domain.CommentLike;
import hanghae99.ditto.comment.domain.CommentLikeRepository;
import hanghae99.ditto.comment.domain.CommentRepository;
import hanghae99.ditto.comment.dto.request.CommentRequest;
import hanghae99.ditto.comment.dto.response.CommentLikeResponse;
import hanghae99.ditto.comment.dto.response.CommentResponse;
import hanghae99.ditto.global.entity.UsageStatus;
import hanghae99.ditto.member.domain.FollowRepository;
import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.newsfeed.dto.request.NewsfeedRequest;
import hanghae99.ditto.newsfeed.service.NewsfeedService;
import hanghae99.ditto.post.domain.Post;
import hanghae99.ditto.post.domain.PostRepository;
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
    private final NewsfeedService newsfeedService;

    @Transactional
    public CommentResponse uploadComment(Member member, Long postId, CommentRequest commentRequest) {
        Post post = checkPostAvailability(postId);
        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .content(commentRequest.getContent()).build();
        commentRepository.save(comment);

        Member receiver = post.getMember();
        followRepository.findAllByToMemberId(member.getId()).forEach(
                follow -> {
                    NewsfeedRequest newsfeedRequest = new NewsfeedRequest(follow.getFromMember().getId(), member.getId(), receiver.getId(), "COMMENT", post.getTitle());
                    newsfeedService.createNewsfeed(newsfeedRequest);
                }
        );

        return new CommentResponse(comment);
    }

    public List<CommentResponse> getComments(Long postId) {
        checkPostAvailability(postId);
        return commentRepository.findAllByPostId(postId).stream().map(
                comment -> new CommentResponse(comment)
        ).collect(Collectors.toList());

    }

    @Transactional
    public CommentResponse updateComment(Member member, Long postId, Long commentId, CommentRequest commentRequest) {
        checkPostAvailability(postId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 댓글입니다.");
        });
        if (member.equals(comment.getMember())){
            if (!isCommentDeleted(comment)) {
                comment.updateContent(commentRequest.getContent());
            } else {
                throw new IllegalStateException("삭제한 댓글은 수정할 수 없습니다.");
            }
        } else{
            throw new RuntimeException("권한이 없는 멤버입니다.");
        }
        return new CommentResponse(comment);
    }

    @Transactional
    public CommentResponse deleteComment(Member member,Long postId, Long commentId) {
        checkPostAvailability(postId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 댓글입니다.");
        });
        if (member.equals(comment.getMember())){
            if (!isCommentDeleted(comment)){
                comment.deleteComment();
            } else {
                throw new IllegalStateException("이미 삭제된 댓글입니다.");
            }
        }else {
            throw new RuntimeException("권한이 없는 멤버입니다.");
        }
        return new CommentResponse(comment);
    }

    @Transactional
    public CommentLikeResponse pushCommentLike(Member member, Long postId, Long commentId){
        Post post = checkPostAvailability(postId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 댓글입니다.");
        });
        if (isCommentDeleted(comment)){
            throw new IllegalStateException("삭제된 댓글입니다.");
        }
        CommentLike commentLike = commentLikeRepository.findByMemberIdAndCommentId(member.getId(), commentId).orElse(null);

        boolean flag = true;
        if (commentLike != null){
            if (commentLike.getStatus().equals(UsageStatus.ACTIVE)){
                commentLike.deleteCommentLike();
                flag = false;
            } else {
                commentLike.pushCommentLike();
            }
        } else {
            commentLike = CommentLike.builder()
                    .member(member)
                    .comment(comment).build();
            commentLikeRepository.save(commentLike);
        }

        if (flag){
            followRepository.findAllByToMemberId(member.getId()).forEach(
                    follow -> {
                        NewsfeedRequest newsfeedRequest = new NewsfeedRequest(follow.getFromMember().getId(), member.getId(), post.getMember().getId(), "COMMENTLIKE", null);
                        newsfeedService.createNewsfeed(newsfeedRequest);
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
            throw new IllegalArgumentException("유효하지 않은 게시글입니다.");
        });
        if (post.getStatus().equals(UsageStatus.DELETED)){
            throw new IllegalArgumentException("삭제된 게시글입니다.");
        }

        return post;
    }
}
