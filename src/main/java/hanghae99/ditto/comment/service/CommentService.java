package hanghae99.ditto.comment.service;

import hanghae99.ditto.comment.dto.request.CommentRequest;
import hanghae99.ditto.comment.dto.response.CommentLikeResponse;
import hanghae99.ditto.comment.dto.response.CommentResponse;
import hanghae99.ditto.member.domain.Member;

import java.util.List;

public interface CommentService {
    CommentResponse uploadComment(Member member, Long postId, CommentRequest commentRequest);

    List<CommentResponse> getComments(Long postId);

    CommentResponse updateComment(Member member,Long postId, Long commentId, CommentRequest commentRequest);

    CommentResponse deleteComment(Member member,Long postId, Long commentId);

    CommentLikeResponse pushCommentLike(Member member,Long postId, Long commentId);
}
