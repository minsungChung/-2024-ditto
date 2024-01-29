package hanghae99.ditto.member.controller;

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
    public FollowResponse followMember(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("toMemberId") Long toMemberId){
        return followService.followMember(principalDetails.getMember(), toMemberId);
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
