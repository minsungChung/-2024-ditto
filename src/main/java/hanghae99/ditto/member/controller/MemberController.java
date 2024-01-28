package hanghae99.ditto.member.controller;

import hanghae99.ditto.member.dto.request.MemberInfoRequest;
import hanghae99.ditto.member.dto.request.MemberJoinRequest;
import hanghae99.ditto.member.dto.request.MemberPasswordRequest;
import hanghae99.ditto.member.dto.response.MemberJoinResponse;
import hanghae99.ditto.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public MemberJoinResponse joinMember(@Valid @RequestBody MemberJoinRequest memberJoinRequest){
        return memberService.saveMember(memberJoinRequest);
    }

    @PatchMapping("/{memberId}")
    public void updateMemberInfo(@PathVariable("memberId") Long memberId,@Valid @RequestBody MemberInfoRequest memberInfoRequest){
       memberService.updateMemberInfo(memberId, memberInfoRequest);
    }

    @PatchMapping("/{memberId}/password")
    public void updateMemberPassword(@PathVariable("memberId") Long memberId,@Valid @RequestBody MemberPasswordRequest memberPasswordRequest){
        memberService.updateMemberPassword(memberId, memberPasswordRequest);
    }

}
