package org.example.service;

import org.example.dto.request.MemberInfoRequest;
import org.example.dto.request.MemberJoinRequest;
import org.example.dto.request.MemberPasswordRequest;
import org.example.dto.response.MemberJoinResponse;
import org.example.dto.response.UpdateMemberResponse;
import org.example.global.dto.MemberDto;

public interface MemberService {
    MemberJoinResponse saveMember(MemberJoinRequest memberJoinRequest);

    UpdateMemberResponse updateMemberInfo(Long myId, Long memberId, MemberInfoRequest memberInfoRequest);

    UpdateMemberResponse updateMemberPassword(Long myId, Long memberId, MemberPasswordRequest memberPasswordRequest);

    MemberDto getOneMember(Long memberId);
}
