package hanghae99.ditto.comment.controller;

import hanghae99.ditto.comment.dto.request.CommentRequest;
import hanghae99.ditto.comment.dto.response.CommentResponse;
import hanghae99.ditto.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponse uploadComment(@PathVariable("postId") Long postId, @RequestBody CommentRequest commentRequest){
        return commentService.uploadComment(postId, commentRequest);
    }

    @GetMapping()
    public List<CommentResponse> getComments(@PathVariable("postId") Long postId){
        return commentService.getComments(postId);
    }

    @PatchMapping("/{commentId}")
    public CommentResponse updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentRequest commentRequest){
        return commentService.updateComment(commentId, commentRequest);
    }

    @DeleteMapping("/{commentId}")
    public CommentResponse deleteComment(@PathVariable("commentId") Long commentId){
        return commentService.deleteComment(commentId);
    }


}
