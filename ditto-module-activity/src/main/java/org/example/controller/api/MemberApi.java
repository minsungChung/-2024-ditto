package org.example.controller.api;

import org.example.global.dto.MemberDto;
import org.example.global.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "client1", url = "http://user:8087/api/members")
public interface MemberApi {
    @GetMapping("/{memberId}")
    BaseResponse<MemberDto> findById(@PathVariable("memberId") Long memberId);

    @GetMapping()
    BaseResponse<MemberDto> findByEmail(@RequestParam("memberEmail") String memberEmail);
}
