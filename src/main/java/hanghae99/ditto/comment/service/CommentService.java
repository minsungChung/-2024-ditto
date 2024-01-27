package hanghae99.ditto.comment.service;

import hanghae99.ditto.comment.dto.request.CommentRequest;
import hanghae99.ditto.comment.dto.response.CommentLikeResponse;
import hanghae99.ditto.comment.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse uploadComment(Long postId, CommentRequest commentRequest);

    List<CommentResponse> getComments(Long postId);

    CommentResponse updateComment(Long commentId, CommentRequest commentRequest);

    CommentResponse deleteComment(Long commentId);

    CommentLikeResponse pushCommentLike(Long commentId);
}
