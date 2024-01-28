package hanghae99.ditto.newsfeed.service;

import hanghae99.ditto.global.entity.UsageStatus;
import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.member.domain.MemberRepository;
import hanghae99.ditto.newsfeed.domain.Newsfeed;
import hanghae99.ditto.newsfeed.domain.NewsfeedRepository;
import hanghae99.ditto.newsfeed.dto.request.NewsfeedRequest;
import hanghae99.ditto.newsfeed.dto.response.NewsfeedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsfeedServiceImpl implements NewsfeedService{

    private final NewsfeedRepository newsfeedRepository;
    private final MemberRepository memberRepository;

    public List<NewsfeedResponse> showNewsfeed(){
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Newsfeed> newsfeeds = newsfeedRepository.findAllByFeedMemberId(member.getId());
        return newsfeeds.stream().filter(newsfeed -> newsfeed.getStatus().equals(UsageStatus.ACTIVE)).map(newsfeed ->
                new NewsfeedResponse(newsfeed.getMessage())).collect(Collectors.toList());
    }

    @Transactional
    public void createNewsfeed(NewsfeedRequest newsfeedRequest){

        Member feedOwner = memberRepository.findById(newsfeedRequest.getFeedMemberId()).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 멤버입니다.");
        });
        Member sender = memberRepository.findById(newsfeedRequest.getSenderId()).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 멤버입니다.");
        });
        Member receiver = memberRepository.findById(newsfeedRequest.getReceiverId()).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 멤버입니다.");
        });

        String message = sender.getMemberName() + "님이 ";
        if (checkSameMember(feedOwner.getId(), receiver.getId())){
            if (newsfeedRequest.getType().equals("COMMENT")){
                message += "내 게시글에 댓글을 달았습니다.";
            } else if (newsfeedRequest.getType().equals("POSTLIKE")) {
                message += "내 게시글에 좋아요를 눌렀습니다.";
            } else if (newsfeedRequest.getType().equals("COMMENTLIKE")) {
                message += "내 댓글에 좋아요를 눌렀습니다.";
            } else if (newsfeedRequest.getType().equals("FOLLOW")) {
                message += "나를 팔로우했습니다.";
            }
        } else {
            if (newsfeedRequest.getType().equals("COMMENT")){
                message += receiver.getMemberName()+"님의 게시글에 댓글을 달았습니다.";
            } else if (newsfeedRequest.getType().equals("POSTLIKE")) {
                message += receiver.getMemberName()+"님의 게시글에 좋아요를 눌렀습니다.";
            } else if (newsfeedRequest.getType().equals("COMMENTLIKE")) {
                message += receiver.getMemberName()+"님의 댓글에 좋아요를 눌렀습니다.";
            } else if (newsfeedRequest.getType().equals("FOLLOW")) {
                message += receiver.getMemberName()+"님을 팔로우했습니다.";
            }
        }
        System.out.println(feedOwner.getId() + message);
        newsfeedRepository.save(Newsfeed.builder()
                .feedMember(feedOwner)
                .sender(sender)
                .receiver(receiver)
                .message(message).build());
    }

    public boolean checkSameMember(Long memberId1, Long memberId2){
        if (memberId1.equals(memberId2)){
            return true;
        }
        return false;
    }
}
