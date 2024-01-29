package hanghae99.ditto.newsfeed.controller;

import hanghae99.ditto.newsfeed.dto.response.NewsfeedResponse;
import hanghae99.ditto.newsfeed.service.NewsfeedService;
import hanghae99.ditto.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/newsfeed")
public class NewsfeedController {

    private final NewsfeedService newsfeedService;

    @GetMapping
    public Page<NewsfeedResponse> showNewsfeed(@PageableDefault(size = 20, sort = "createDate", direction = Sort.Direction.DESC)Pageable pageable){
        return newsfeedService.showNewsfeed(pageable);
    }

    @GetMapping("/posts")
    public Page<PostResponse> showPostNewsfeed(@PageableDefault(size = 20, sort = "createDate", direction = Sort.Direction.DESC)Pageable pageable){
        return newsfeedService.showPostNewsfeed(pageable);
    }
}
