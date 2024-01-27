package hanghae99.ditto.post.controller;

import hanghae99.ditto.post.dto.response.PostLikeResponse;
import hanghae99.ditto.post.service.PostService;
import hanghae99.ditto.post.dto.request.PostRequest;
import hanghae99.ditto.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponse uploadPost(@RequestBody PostRequest postRequest){
        return postService.uploadPost(postRequest);
    }

    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable("postId") Long postId){
        return postService.getPost(postId);
    }

    @PatchMapping("/{postId}")
    public PostResponse updatePost(@PathVariable("postId") Long postId, @RequestBody PostRequest postRequest){
        return postService.updatePost(postId, postRequest);
    }

    @DeleteMapping("/{postId}")
    public PostResponse deletePost(@PathVariable("postId") Long postId){
        return postService.deletePost(postId);
    }

    @PostMapping("/{postId}/like")
    public PostLikeResponse pushPostLike(@PathVariable("postId") Long postId){
        return postService.pushPostLike(postId);
    }


}
