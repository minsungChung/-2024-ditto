package hanghae99.ditto.member.service;

import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.member.dto.request.MemberInfoRequest;
import hanghae99.ditto.member.dto.request.MemberJoinRequest;
import hanghae99.ditto.member.dto.request.MemberPasswordRequest;
import hanghae99.ditto.member.dto.response.MemberJoinResponse;
import hanghae99.ditto.member.dto.response.UpdateMemberResponse;

public interface MemberService {
    MemberJoinResponse saveMember(MemberJoinRequest memberJoinRequest);

    UpdateMemberResponse updateMemberInfo(Member member, Long memberId, MemberInfoRequest memberInfoRequest);

    UpdateMemberResponse updateMemberPassword(Member member, Long memberId, MemberPasswordRequest memberPasswordRequest);
}
