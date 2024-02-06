package org.example.service;

import org.example.controller.api.MemberApi;
import org.example.controller.api.NewsfeedApi;
import org.example.domain.FollowRepository;
import org.example.global.dto.NewsfeedRequest;
import org.example.global.entity.UsageStatus;
import org.example.domain.Follow;

import org.example.dto.response.FollowResponse;
import org.example.dto.response.FollowMemberResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final NewsfeedApi newsfeedApi;
    private final MemberApi memberApi;

    @Transactional
    public FollowResponse followMember(Long memberId, Long toMemberId) {
        Follow follow = followRepository.findByFromMemberIdAndToMemberId(memberId, toMemberId).orElse(null);
        boolean flag = true;

        if (follow != null){
            if (follow.getStatus().equals(UsageStatus.ACTIVE)){
                follow.deleteFollow();
                flag = false;
            } else{
                follow.activateFollow();
            }
        } else {
            follow = Follow.builder()
                    .fromMember(memberId)
                    .toMemberId(toMemberId)
                    .build();
            followRepository.save(follow);
        }
        if (flag){
            Follow finalFollow = follow;
            followRepository.findAllByToMemberId(memberId).forEach(
                    follow1 -> {
                        NewsfeedRequest newsfeedRequest = new NewsfeedRequest(follow1.getFromMemberId(), memberId, finalFollow.getToMemberId(), "FOLLOW", null);
                        newsfeedApi.createNewsfeed(newsfeedRequest);
                    }
            );
        }

        return new FollowResponse(follow.getFromMemberId(), follow.getToMemberId());
    }

    public List<FollowMemberResponse> getFollowings(Long memberId){
        List<FollowMemberResponse> followings = followRepository.findAllByFromMemberId(memberId).stream()
                .filter(follow -> follow.getStatus().equals(UsageStatus.ACTIVE)).map(
                follow -> new FollowMemberResponse(memberApi.findById(memberId).getResult())).collect(Collectors.toList());
        return followings;
    }

    public List<FollowMemberResponse> getFollowers(Long memberId){
        List<FollowMemberResponse> followers = followRepository.findAllByToMemberId(memberId).stream()
                .filter(follow -> follow.getStatus().equals(UsageStatus.ACTIVE)).map(
                follow -> new FollowMemberResponse(memberApi.findById(memberId).getResult())).collect(Collectors.toList());
        return followers;
    }

}
