package hanghae99.ditto.member.service;

import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.member.dto.response.FollowResponse;
import hanghae99.ditto.member.dto.response.FollowMemberResponse;

import java.util.List;

public interface FollowService {

    FollowResponse followMember(Member member, Long toMemberId);
    List<FollowMemberResponse> getFollowings(Long memberId);
    List<FollowMemberResponse> getFollowers(Long memberId);
}
