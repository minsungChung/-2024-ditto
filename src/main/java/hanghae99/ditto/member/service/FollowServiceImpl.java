package hanghae99.ditto.member.service;

import hanghae99.ditto.global.entity.UsageStatus;
import hanghae99.ditto.member.domain.Follow;
import hanghae99.ditto.member.domain.FollowRepository;
import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.member.domain.MemberRepository;
import hanghae99.ditto.member.dto.response.FollowResponse;
import hanghae99.ditto.member.dto.response.FollowMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowServiceImpl implements FollowService{

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    @Transactional
    public FollowResponse followMember(Long toMemberId) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Follow follow = followRepository.findByFromMemberIdAndToMemberId(member.getId(), toMemberId).orElse(null);
        System.out.println(follow);
        if (follow != null){
            if (follow.getStatus().equals(UsageStatus.ACTIVE)){
                follow.deleteFollow();
            } else{
                follow.activateFollow();
            }
        } else {
            follow = Follow.builder()
                    .fromMember(member)
                    .toMember(memberRepository.findById(toMemberId).orElseThrow(() -> {
                        throw new IllegalArgumentException("유효하지 않은 아이디입니다");
                    })).build();
            followRepository.save(follow);
        }

        return new FollowResponse(follow.getFromMember(), follow.getToMember());
    }

    public List<FollowMemberResponse> getFollowings(Long memberId){
        List<FollowMemberResponse> followingList = followRepository.findAllByFromMemberId(memberId).stream()
                .filter(follow -> follow.getStatus().equals(UsageStatus.ACTIVE)).map(
                follow -> new FollowMemberResponse(follow.getToMember())).collect(Collectors.toList());
        return followingList;
    }

    public List<FollowMemberResponse> getFollowers(Long memberId){
        List<FollowMemberResponse> followingList = followRepository.findAllByToMemberId(memberId).stream()
                .filter(follow -> follow.getStatus().equals(UsageStatus.ACTIVE)).map(
                follow -> new FollowMemberResponse(follow.getFromMember())).collect(Collectors.toList());
        return followingList;
    }

}
