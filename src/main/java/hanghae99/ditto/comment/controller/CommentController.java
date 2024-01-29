package hanghae99.ditto.comment.controller;

import hanghae99.ditto.comment.dto.request.CommentRequest;
import hanghae99.ditto.comment.dto.response.CommentLikeResponse;
import hanghae99.ditto.comment.dto.response.CommentResponse;
import hanghae99.ditto.comment.service.CommentService;
import hanghae99.ditto.member.domain.PrincipalDetails;
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
    public CommentResponse uploadComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId, @RequestBody CommentRequest commentRequest){
        return commentService.uploadComment(principalDetails.getMember(), postId, commentRequest);
    }

    @GetMapping()
    public List<CommentResponse> getComments(@PathVariable("postId") Long postId){
        return commentService.getComments(postId);
    }

    @PatchMapping("/{commentId}")
    public CommentResponse updateComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @RequestBody CommentRequest commentRequest){
        return commentService.updateComment(principalDetails.getMember(), postId, commentId, commentRequest);
    }

    @DeleteMapping("/{commentId}")
    public CommentResponse deleteComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        return commentService.deleteComment(principalDetails.getMember(), postId, commentId);
    }

    @PostMapping("/{commentId}/like")
    public CommentLikeResponse pushCommentLike(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        return commentService.pushCommentLike(principalDetails.getMember(), postId, commentId);
    }


}
