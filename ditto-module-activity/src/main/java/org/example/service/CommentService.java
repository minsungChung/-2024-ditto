package org.example.service;


import org.example.dto.request.CommentRequest;
import org.example.dto.response.CommentLikeResponse;
import org.example.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse uploadComment(Long memberId, Long postId, CommentRequest commentRequest);

    List<CommentResponse> getComments(Long postId);

    CommentResponse updateComment(Long memberId,Long postId, Long commentId, CommentRequest commentRequest);

    CommentResponse deleteComment(Long memberId,Long postId, Long commentId);

    CommentLikeResponse pushCommentLike(Long memberId, Long postId, Long commentId);
}
