package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.api.MemberApi;
import org.example.controller.api.PostApi;
import org.example.domain.*;
import org.example.global.dto.NewsfeedPostRequest;
import org.example.global.dto.NewsfeedRequest;
import org.example.dto.response.NewsfeedPostResponse;
import org.example.dto.response.NewsfeedResponse;
import org.example.global.dto.MemberDto;
import org.example.global.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsfeedServiceImpl implements NewsfeedService{

    private final NewsfeedRepository newsfeedRepository;
    private final MemberApi memberApi;
    private final PostApi postApi;
    private final NewsfeedPostRepository newsfeedPostRepository;

    public Page<NewsfeedResponse> showNewsfeed(Long memberId, Pageable pageable){
        Page<Newsfeed> newsfeeds = newsfeedRepository.findAllByFeedMemberId(memberId, pageable);
        return newsfeeds.map(newsfeed -> new NewsfeedResponse(newsfeed.getMessage()));
    }

    public Page<NewsfeedPostResponse> showPostNewsfeed(Long memberId, Pageable pageable){
        Page<NewsfeedPost> postNewsfeeds = newsfeedPostRepository.findAllByFeedMemberId(memberId, pageable);

        return postNewsfeeds.map(newsfeedPost -> {
            MemberDto writer = memberApi.findById(newsfeedPost.getWriterMemberId()).getResult();
            PostDto post = postApi.findById(newsfeedPost.getPostId()).getResult();
            return new NewsfeedPostResponse(post, writer.getMemberId(), writer.getMemberName());
        });
    }

    @Transactional
    public String createPostNewsfeed(NewsfeedPostRequest newsfeedPostRequest){
        log.info(String.valueOf(newsfeedPostRequest.getPostId()));
        NewsfeedPost newsfeedPost = NewsfeedPost.builder()
                .feedMemberId(newsfeedPostRequest.getFeedMemberId())
                .postId(newsfeedPostRequest.getPostId())
                .writerMemberId(newsfeedPostRequest.getWriterMemberId()).build();
        newsfeedPostRepository.save(newsfeedPost);
        return "뉴스피드 만들기 성공";
    }

    @Transactional
    public String createNewsfeed(NewsfeedRequest newsfeedRequest){

        MemberDto feedOwner = memberApi.findById(newsfeedRequest.getFeedMemberId()).getResult();
        MemberDto sender = memberApi.findById(newsfeedRequest.getSenderId()).getResult();
        MemberDto receiver = memberApi.findById(newsfeedRequest.getReceiverId()).getResult();

        String message = sender.getMemberName() + "님이 ";
        if (checkSameMember(feedOwner.getMemberId(), receiver.getMemberId())){
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
        System.out.println(feedOwner.getMemberId() + message);
        newsfeedRepository.save(Newsfeed.builder()
                .feedMemberId(feedOwner.getMemberId())
                .senderId(sender.getMemberId())
                .receiverId(receiver.getMemberId())
                .message(message)
                .build());
        return "뉴스피드 만들기 성공";
    }

    public boolean checkSameMember(Long memberId1, Long memberId2){
        if (memberId1.equals(memberId2)){
            return true;
        }
        return false;
    }
}
