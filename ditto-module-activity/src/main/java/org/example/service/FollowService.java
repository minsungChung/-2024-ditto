package org.example.service;

import org.example.dto.response.FollowResponse;
import org.example.dto.response.FollowMemberResponse;

import java.util.List;

public interface FollowService {

    FollowResponse followMember(Long memberId, Long toMemberId);
    List<FollowMemberResponse> getFollowings(Long memberId);
    List<FollowMemberResponse> getFollowers(Long memberId);
}
