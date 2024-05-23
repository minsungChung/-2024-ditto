package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Member", description = "Member API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    @Operation(summary = "회원가입", description = "사용자 정보를 입력하고 회원가입을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    public BaseResponse<MemberJoinResponse> joinMember(@Valid MemberJoinRequest memberJoinRequest){
        return new BaseResponse<>(memberService.saveMember(memberJoinRequest));
    }


    @PatchMapping("/mypage/{memberId}")
    @Operation(summary = "사용자 정보 변경", description = "비밀번호를 제외한 사용자 정보를 변경합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    public BaseResponse<UpdateMemberResponse> updateMemberInfo(
            @Parameter(description = "인가된 사용자 아이디")
            @RequestHeader("memberId") Long myId,
            @Parameter(description = "변경할 사용자 아이디")
            @PathVariable("memberId") Long memberId,
            @Valid @RequestBody MemberInfoRequest memberInfoRequest){
        return new BaseResponse<>(memberService.updateMemberInfo(myId, memberId, memberInfoRequest));
    }


    @PatchMapping("/mypage/{memberId}/password")
    @Operation(summary = "사용자 비밀번호 변경", description = "비밀번호 정보를 변경합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    public BaseResponse<UpdateMemberResponse> updateMemberPassword(
            @Parameter(description = "인가된 사용자 아이디")
            @RequestHeader("memberId") Long myId,
            @Parameter(description = "변경할 사용자 아이디")
            @PathVariable("memberId") Long memberId,
            @Valid @RequestBody MemberPasswordRequest memberPasswordRequest){
        return new BaseResponse<>(memberService.updateMemberPassword(myId, memberId, memberPasswordRequest));
    }

    @GetMapping("/members/{memberId}")
    @Operation(summary = "사용자 정보 조회", description = "해당 사용자 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    public BaseResponse<MemberDto> getMember(@Parameter(description = "조회할 사용자 아이디") @PathVariable("memberId")Long memberId){
        return new BaseResponse<>(memberService.getOneMember(memberId));
    }

    @GetMapping("/members")
    @Operation(summary = "사용자 정보 조회", description = "해당 사용자 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    public BaseResponse<MemberDto> getMemberByEmail(@Parameter(description = "조회할 사용자 아이디") @RequestParam("memberEmail")String memberEmail){
        log.info("제발 좀 되라 이자식아");
        return new BaseResponse<>(memberService.getMemberByEmail(memberEmail));
    }

}
