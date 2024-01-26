package hanghae99.ditto.member.controller;

import hanghae99.ditto.member.dto.response.FollowResponse;
import hanghae99.ditto.member.dto.response.FollowMemberResponse;
import hanghae99.ditto.member.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow/{toMemberId}")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public FollowResponse followMember(@PathVariable("toMemberId") Long toMemberId){
        return followService.followMember(toMemberId);
    }

    @GetMapping("/followings")
    public List<FollowMemberResponse> getFollowings(@PathVariable("toMemberId") Long memberId){
        return followService.getFollowings(memberId);
    }

    @GetMapping("/followers")
    public List<FollowMemberResponse> getFollowers(@PathVariable("toMemberId") Long memberId){
        return followService.getFollowers(memberId);
    }
}
