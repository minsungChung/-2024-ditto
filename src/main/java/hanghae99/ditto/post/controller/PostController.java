package hanghae99.ditto.post.controller;

import hanghae99.ditto.member.domain.PrincipalDetails;
import hanghae99.ditto.post.dto.response.PostLikeResponse;
import hanghae99.ditto.post.service.PostService;
import hanghae99.ditto.post.dto.request.PostRequest;
import hanghae99.ditto.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponse uploadPost(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody PostRequest postRequest){
        return postService.uploadPost(principalDetails.getMember(), postRequest);
    }

    @GetMapping("/{postId}")
    public PostResponse getPost(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable("postId") Long postId){
        return postService.getPost(principalDetails.getMember(), postId);
    }

    @PatchMapping("/{postId}")
    public PostResponse updatePost(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId, @RequestBody PostRequest postRequest){
        return postService.updatePost(principalDetails.getMember(), postId, postRequest);
    }

    @DeleteMapping("/{postId}")
    public PostResponse deletePost(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId){
        return postService.deletePost(principalDetails.getMember(), postId);
    }

    @PostMapping("/{postId}/like")
    public PostLikeResponse pushPostLike(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId){
        return postService.pushPostLike(principalDetails.getMember(), postId);
    }


}
