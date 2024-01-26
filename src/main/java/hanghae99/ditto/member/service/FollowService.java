package hanghae99.ditto.member.service;

import hanghae99.ditto.member.dto.response.FollowResponse;

public interface FollowService {

    FollowResponse followMember(Long toMemberId);
}
