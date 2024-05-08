package org.example.controller;

import groovy.util.logging.Slf4j;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.Member;
import org.example.dto.request.MemberJoinRequest;
import org.example.dto.request.MemberPasswordRequest;
import org.example.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MyRestController {

    private static final Logger log = LoggerFactory.getLogger(MyRestController.class);
    private final MyService myService;

    @GetMapping("/member/{id}")
    public Member getMember(@PathVariable("id") String no){
        return myService.getMember(no);
    }

    @PostMapping("/member")
    public int createMember(@Valid @RequestBody MemberJoinRequest memberJoinRequest){
        log.info("여기까지 오케이~~");
        return myService.saveMember(memberJoinRequest);
    }

    @PatchMapping("/mypage/{memberId}/password")
    public int updateMemberPassword(@RequestHeader("memberId") Long myId, @PathVariable("memberId") Long memberId, @Valid @RequestBody MemberPasswordRequest memberPasswordRequest){
        return myService.updateMemberPassword(myId, memberId, memberPasswordRequest);
    }

    @DeleteMapping("/member/{memberId}")
    public int deleteMember(@RequestHeader("memberId") Long myId, @PathVariable("memberId") Long memberId){
        return myService.deleteMember(myId, memberId);
    }

}
