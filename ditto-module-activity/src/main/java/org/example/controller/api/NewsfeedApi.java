package org.example.controller.api;

import org.example.global.dto.NewsfeedPostRequest;
import org.example.global.dto.NewsfeedRequest;
import org.example.global.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "client2", url = "http://newsfeed:8082/api/newsfeed")
public interface NewsfeedApi {

    @PostMapping
    BaseResponse<String> createNewsfeed(@RequestBody NewsfeedRequest newsfeedRequest);
    @PostMapping("/posts")
    BaseResponse<String> createNewsfeedPost(@RequestBody NewsfeedPostRequest newsfeedPostRequest);

}
