package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.global.dto.MemberDto;
import org.example.global.response.BaseResponse;

import org.example.dto.request.MemberInfoRequest;
import org.example.dto.request.MemberJoinRequest;
import org.example.dto.request.MemberPasswordRequest;
import org.example.dto.response.MemberJoinResponse;
import org.example.dto.response.UpdateMemberResponse;
import org.example.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public BaseResponse<MemberJoinResponse> joinMember(@Valid @RequestBody MemberJoinRequest memberJoinRequest){
        return new BaseResponse<>(memberService.saveMember(memberJoinRequest));
    }

    @PatchMapping("/mypage/{memberId}")
    public BaseResponse<UpdateMemberResponse> updateMemberInfo(@RequestHeader("memberId") Long myId, @PathVariable("memberId") Long memberId, @Valid @RequestBody MemberInfoRequest memberInfoRequest){
        log.info(String.valueOf(myId));
        return new BaseResponse<>(memberService.updateMemberInfo(myId, memberId, memberInfoRequest));
    }

    @PatchMapping("/mypage/{memberId}/password")
    public BaseResponse<UpdateMemberResponse> updateMemberPassword(@RequestHeader("memberId") Long myId, @PathVariable("memberId") Long memberId,@Valid @RequestBody MemberPasswordRequest memberPasswordRequest){
        return new BaseResponse<>(memberService.updateMemberPassword(myId, memberId, memberPasswordRequest));
    }

    @GetMapping("/members/{memberId}")
    public BaseResponse<MemberDto> getMember(@PathVariable("memberId")Long memberId){
        return new BaseResponse<>(memberService.getOneMember(memberId));
    }

}
