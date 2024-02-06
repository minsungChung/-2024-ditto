package org.example.controller;


import org.example.dto.response.NewsfeedPostResponse;
import org.example.global.dto.NewsfeedPostRequest;
import org.example.global.dto.NewsfeedRequest;
import org.example.global.response.BaseResponse;

import org.example.dto.response.NewsfeedResponse;
import org.example.service.NewsfeedService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/newsfeed")
public class NewsfeedController {

    private final NewsfeedService newsfeedService;

    @GetMapping
    public BaseResponse<Page<NewsfeedResponse>> showNewsfeed(@RequestHeader("memberId") Long memberId, @PageableDefault(size = 20, sort = "createDate", direction = Sort.Direction.DESC)Pageable pageable){
        return new BaseResponse<>(newsfeedService.showNewsfeed(memberId, pageable));
    }

    @GetMapping("/posts")
    public BaseResponse<Page<NewsfeedPostResponse>> showPostNewsfeed(@RequestHeader("memberId") Long memberId, @PageableDefault(size = 20, sort = "createDate", direction = Sort.Direction.DESC)Pageable pageable){
        return new BaseResponse<>(newsfeedService.showPostNewsfeed(memberId, pageable));
    }

    @PostMapping("/posts")
    public BaseResponse<String> createPostNewsfeed(@RequestBody NewsfeedPostRequest newsfeedPostRequest){
        return new BaseResponse<>(newsfeedService.createPostNewsfeed(newsfeedPostRequest));
    }

    @PostMapping
    public BaseResponse<String> createNewsfeed(@RequestBody NewsfeedRequest newsfeedRequest){
        return new BaseResponse<>(newsfeedService.createNewsfeed(newsfeedRequest));
    }
}
