package org.example.controller;


import org.example.global.response.BaseResponse;
import org.example.dto.response.FollowMemberResponse;
import org.example.dto.response.FollowResponse;
import org.example.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow/{toMemberId}")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public BaseResponse<FollowResponse> followMember(@RequestHeader("memberId") Long memberId, @PathVariable("toMemberId") Long toMemberId){
        return new BaseResponse<>(followService.followMember(memberId, toMemberId));
    }

    @GetMapping("/followings")
    public BaseResponse<List<FollowMemberResponse>> getFollowings(@PathVariable("toMemberId") Long memberId){
        return new BaseResponse<>(followService.getFollowings(memberId));
    }

    @GetMapping("/followers")
    public BaseResponse<List<FollowMemberResponse>> getFollowers(@PathVariable("toMemberId") Long memberId){
        return new BaseResponse<>(followService.getFollowers(memberId));
    }
}
