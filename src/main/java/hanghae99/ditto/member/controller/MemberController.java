package hanghae99.ditto.member.controller;

import hanghae99.ditto.global.response.BaseResponse;
import hanghae99.ditto.member.domain.PrincipalDetails;
import hanghae99.ditto.member.dto.request.MemberInfoRequest;
import hanghae99.ditto.member.dto.request.MemberJoinRequest;
import hanghae99.ditto.member.dto.request.MemberPasswordRequest;
import hanghae99.ditto.member.dto.response.MemberJoinResponse;
import hanghae99.ditto.member.dto.response.UpdateMemberResponse;
import hanghae99.ditto.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public BaseResponse<MemberJoinResponse> joinMember(@Valid @RequestBody MemberJoinRequest memberJoinRequest){
        return new BaseResponse<>(memberService.saveMember(memberJoinRequest));
    }

    @PatchMapping("/{memberId}")
    public BaseResponse<UpdateMemberResponse> updateMemberInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("memberId") Long memberId, @Valid @RequestBody MemberInfoRequest memberInfoRequest){
        return new BaseResponse<>(memberService.updateMemberInfo(principalDetails.getMember(), memberId, memberInfoRequest));
    }

    @PatchMapping("/{memberId}/password")
    public BaseResponse<UpdateMemberResponse> updateMemberPassword(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("memberId") Long memberId,@Valid @RequestBody MemberPasswordRequest memberPasswordRequest){
        return new BaseResponse<>(memberService.updateMemberPassword(principalDetails.getMember(), memberId, memberPasswordRequest));
    }

}
