package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.response.PostResponse;
import org.example.dto.response.PostSimpleRes;
import org.example.global.dto.PostDto;
import org.example.global.response.BaseResponse;
import org.example.dto.response.PostLikeResponse;
import org.example.service.PostService;
import org.example.dto.request.PostRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public BaseResponse<PostResponse> uploadPost(@RequestHeader("memberId") Long memberId, @Valid @RequestBody PostRequest postRequest){

        return new BaseResponse<>(postService.uploadPost(memberId, postRequest));
    }

//    @GetMapping("/{postId}")
//    public BaseResponse<PostResponse> getPost(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable("postId") Long postId){
//        return new BaseResponse<>(postService.getPost(principalDetails.getMember().getId(), postId));
//    }

    @PatchMapping("/{postId}")
    public BaseResponse<PostSimpleRes> updatePost(@RequestHeader("memberId") Long memberId, @PathVariable("postId") Long postId, @Valid @RequestBody PostRequest postRequest){
        return new BaseResponse<>(postService.updatePost(memberId, postId, postRequest));
    }

    @DeleteMapping("/{postId}")
    public BaseResponse<PostSimpleRes> deletePost(@RequestHeader("memberId") Long memberId, @PathVariable("postId") Long postId){
        return new BaseResponse<>(postService.deletePost(memberId, postId));
    }

    @PostMapping("/{postId}/like")
    public BaseResponse<PostLikeResponse> pushPostLike(@RequestHeader("memberId") Long memberId, @PathVariable("postId") Long postId){
        return new BaseResponse<>(postService.pushPostLike(memberId, postId));
    }

    @GetMapping("/{postId}")
    public BaseResponse<PostDto> getOnePost(@PathVariable("postId")Long postId){
        return new BaseResponse<>(postService.getOnePost(postId));
    }

    @GetMapping("/stocks/{stockId}")
    public BaseResponse<Page<PostDto>> findPostsByStockId(@PathVariable("stockId") Long stockId, @PageableDefault(size = 30) Pageable pageable){
        return new BaseResponse<>(postService.getPostsByStockId(stockId, pageable));
    }


}
