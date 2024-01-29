package hanghae99.ditto.comment.controller;

import hanghae99.ditto.comment.dto.request.CommentRequest;
import hanghae99.ditto.comment.dto.response.CommentLikeResponse;
import hanghae99.ditto.comment.dto.response.CommentResponse;
import hanghae99.ditto.comment.service.CommentService;
import hanghae99.ditto.global.response.BaseResponse;
import hanghae99.ditto.member.domain.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public BaseResponse<CommentResponse> uploadComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId, @Valid @RequestBody CommentRequest commentRequest){
        return new BaseResponse<>(commentService.uploadComment(principalDetails.getMember(), postId, commentRequest));
    }

    @GetMapping()
    public BaseResponse<List<CommentResponse>> getComments(@PathVariable("postId") Long postId){
        return new BaseResponse<>(commentService.getComments(postId));
    }

    @PatchMapping("/{commentId}")
    public BaseResponse<CommentResponse> updateComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @Valid @RequestBody CommentRequest commentRequest){
        return new BaseResponse<>(commentService.updateComment(principalDetails.getMember(), postId, commentId, commentRequest));
    }

    @DeleteMapping("/{commentId}")
    public BaseResponse<CommentResponse> deleteComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        return new BaseResponse<>(commentService.deleteComment(principalDetails.getMember(), postId, commentId));
    }

    @PostMapping("/{commentId}/like")
    public BaseResponse<CommentLikeResponse> pushCommentLike(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        return new BaseResponse<>(commentService.pushCommentLike(principalDetails.getMember(), postId, commentId));
    }


}
