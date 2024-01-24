package hanghae99.ditto.member.controller;

import hanghae99.ditto.member.dto.request.MemberJoinRequest;
import hanghae99.ditto.member.dto.response.MemberJoinResponse;
import hanghae99.ditto.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public MemberJoinResponse joinMember(@RequestBody MemberJoinRequest memberJoinRequest){
        return memberService.saveMember(memberJoinRequest);
    }
}
