package org.example.controller;

import org.example.dto.request.CommentRequest;
import org.example.dto.response.CommentLikeResponse;
import org.example.dto.response.CommentResponse;
import org.example.service.CommentService;
import org.example.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public BaseResponse<CommentResponse> uploadComment(@RequestHeader("memberId") Long memberId, @PathVariable("postId") Long postId, @Valid @RequestBody CommentRequest commentRequest){
        return new BaseResponse<>(commentService.uploadComment(memberId, postId, commentRequest));
    }

    @GetMapping()
    public BaseResponse<List<CommentResponse>> getComments(@PathVariable("postId") Long postId){
        return new BaseResponse<>(commentService.getComments(postId));
    }

    @PatchMapping("/{commentId}")
    public BaseResponse<CommentResponse> updateComment(@RequestHeader("memberId") Long memberId, @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @Valid @RequestBody CommentRequest commentRequest){
        return new BaseResponse<>(commentService.updateComment(memberId, postId, commentId, commentRequest));
    }

    @DeleteMapping("/{commentId}")
    public BaseResponse<CommentResponse> deleteComment(@RequestHeader("memberId") Long memberId, @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        return new BaseResponse<>(commentService.deleteComment(memberId, postId, commentId));
    }

    @PostMapping("/{commentId}/like")
    public BaseResponse<CommentLikeResponse> pushCommentLike(@RequestHeader("memberId") Long memberId, @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        return new BaseResponse<>(commentService.pushCommentLike(memberId, postId, commentId));
    }


}
