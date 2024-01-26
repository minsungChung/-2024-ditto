package hanghae99.ditto.post.controller;

import hanghae99.ditto.post.service.PostService;
import hanghae99.ditto.post.dto.request.PostRequest;
import hanghae99.ditto.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponse uploadPost(@RequestBody PostRequest postRequest){
        return postService.uploadPost(postRequest);
    }
}
