package org.example.controller.api;

import org.example.global.dto.PostDto;
import org.example.global.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "postClient", url = "http://activity:8081/api/posts")
public interface PostApi {
    @GetMapping("/stocks/{stockId}")
    BaseResponse<Page<PostDto>> findPostsByStockId(@PathVariable("stockId") Long stockId);
}
