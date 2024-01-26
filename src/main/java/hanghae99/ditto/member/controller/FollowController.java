package hanghae99.ditto.member.controller;

import hanghae99.ditto.member.dto.response.FollowResponse;
import hanghae99.ditto.member.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow/{toMemberId}")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public FollowResponse followMember(@PathVariable("toMemberId") Long toMemberId){
        return followService.followMember(toMemberId);
    }
}
