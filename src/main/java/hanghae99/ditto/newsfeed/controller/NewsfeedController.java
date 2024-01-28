package hanghae99.ditto.newsfeed.controller;

import hanghae99.ditto.newsfeed.dto.response.NewsfeedResponse;
import hanghae99.ditto.newsfeed.service.NewsfeedService;
import hanghae99.ditto.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/newsfeed")
public class NewsfeedController {

    private final NewsfeedService newsfeedService;

    @GetMapping
    public List<NewsfeedResponse> showNewsfeed(){
        return newsfeedService.showNewsfeed();
    }

    @GetMapping("/posts")
    public List<PostResponse> showPostNewsfeed(){
        return newsfeedService.showPostNewsfeed();
    }
}
