package hanghae99.ditto.newsfeed.service;

import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.member.domain.MemberRepository;
import hanghae99.ditto.member.exception.NoSuchMemberException;
import hanghae99.ditto.newsfeed.domain.Newsfeed;
import hanghae99.ditto.newsfeed.domain.NewsfeedPost;
import hanghae99.ditto.newsfeed.domain.NewsfeedPostRepository;
import hanghae99.ditto.newsfeed.domain.NewsfeedRepository;
import hanghae99.ditto.newsfeed.dto.request.NewsfeedRequest;
import hanghae99.ditto.newsfeed.dto.response.NewsfeedResponse;
import hanghae99.ditto.post.domain.Post;
import hanghae99.ditto.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsfeedServiceImpl implements NewsfeedService{

    private final NewsfeedRepository newsfeedRepository;
    private final MemberRepository memberRepository;
    private final NewsfeedPostRepository newsfeedPostRepository;

    public Page<NewsfeedResponse> showNewsfeed(Member member, Pageable pageable){
        Page<Newsfeed> newsfeeds = newsfeedRepository.findAllByFeedMemberId(member.getId(), pageable);
        return newsfeeds.map(newsfeed -> new NewsfeedResponse(newsfeed.getMessage()));
    }

    public Page<PostResponse> showPostNewsfeed(Member member, Pageable pageable){
        Page<NewsfeedPost> postNewsfeeds = newsfeedPostRepository.findAllByFeedMemberId(member.getId(), pageable);

        return postNewsfeeds.map(newsfeedPost -> new PostResponse(newsfeedPost.getPost()));
    }

    @Transactional
    public void createPostNewsfeed(Member feedMember, Post post){
        NewsfeedPost newsfeedPost = NewsfeedPost.builder()
                .feedMember(feedMember)
                .post(post).build();
        newsfeedPostRepository.save(newsfeedPost);
    }

    @Transactional
    public void createNewsfeed(NewsfeedRequest newsfeedRequest){

        Member feedOwner = memberRepository.findById(newsfeedRequest.getFeedMemberId()).orElseThrow(() -> {
            throw new NoSuchMemberException();
        });
        Member sender = memberRepository.findById(newsfeedRequest.getSenderId()).orElseThrow(() -> {
            throw new NoSuchMemberException();
        });
        Member receiver = memberRepository.findById(newsfeedRequest.getReceiverId()).orElseThrow(() -> {
            throw new NoSuchMemberException();
        });

        String message = sender.getMemberName() + "님이 ";
        if (checkSameMember(feedOwner.getId(), receiver.getId())){
            if (newsfeedRequest.getType().equals("COMMENT")){
                message += newsfeedRequest.getPostTitle() + " 게시글에 댓글을 달았습니다.";
            } else if (newsfeedRequest.getType().equals("POSTLIKE")) {
                message += newsfeedRequest.getPostTitle() + " 게시글에 좋아요를 눌렀습니다.";
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
