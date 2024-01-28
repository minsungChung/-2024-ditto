package hanghae99.ditto.newsfeed.service;

import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.newsfeed.dto.request.NewsfeedRequest;
import hanghae99.ditto.newsfeed.dto.response.NewsfeedResponse;
import hanghae99.ditto.post.domain.Post;
import hanghae99.ditto.post.dto.response.PostResponse;

import java.util.List;

public interface NewsfeedService {
    List<NewsfeedResponse> showNewsfeed();

    void createNewsfeed(NewsfeedRequest newsfeedRequest);

    List<PostResponse> showPostNewsfeed();

    void createPostNewsfeed(Member feedMember, Post post);
}
