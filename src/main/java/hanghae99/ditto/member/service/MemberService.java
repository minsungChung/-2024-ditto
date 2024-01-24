package hanghae99.ditto.member.service;

import hanghae99.ditto.member.dto.request.MemberJoinRequest;
import hanghae99.ditto.member.dto.response.MemberJoinResponse;

public interface MemberService {
    MemberJoinResponse saveMember(MemberJoinRequest memberJoinRequest);
}
