package org.example.controller.api;

import org.example.global.dto.PostDto;
import org.example.global.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client4", url = "http://activity:8081/api/posts")
public interface PostApi {
    @GetMapping("/{postId}")
    BaseResponse<PostDto> findById(@PathVariable("postId") Long postId);
}
