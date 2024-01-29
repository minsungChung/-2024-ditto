package hanghae99.ditto.post.controller;

import hanghae99.ditto.global.response.BaseResponse;
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
    public BaseResponse<PostResponse> uploadPost(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody PostRequest postRequest){
        return new BaseResponse(postService.uploadPost(principalDetails.getMember(), postRequest));
    }

    @GetMapping("/{postId}")
    public BaseResponse<PostResponse> getPost(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable("postId") Long postId){
        return new BaseResponse(postService.getPost(principalDetails.getMember(), postId));
    }

    @PatchMapping("/{postId}")
    public BaseResponse<PostResponse> updatePost(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId, @RequestBody PostRequest postRequest){
        return new BaseResponse(postService.updatePost(principalDetails.getMember(), postId, postRequest));
    }

    @DeleteMapping("/{postId}")
    public BaseResponse<PostResponse> deletePost(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId){
        return new BaseResponse(postService.deletePost(principalDetails.getMember(), postId));
    }

    @PostMapping("/{postId}/like")
    public BaseResponse<PostLikeResponse> pushPostLike(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("postId") Long postId){
        return new BaseResponse(postService.pushPostLike(principalDetails.getMember(), postId));
    }


}
