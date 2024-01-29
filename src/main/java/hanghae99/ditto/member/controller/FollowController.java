package hanghae99.ditto.member.controller;

import hanghae99.ditto.global.response.BaseResponse;
import hanghae99.ditto.member.domain.PrincipalDetails;
import hanghae99.ditto.member.dto.response.FollowResponse;
import hanghae99.ditto.member.dto.response.FollowMemberResponse;
import hanghae99.ditto.member.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow/{toMemberId}")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public BaseResponse<FollowResponse> followMember(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("toMemberId") Long toMemberId){
        return new BaseResponse<>(followService.followMember(principalDetails.getMember(), toMemberId));
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
