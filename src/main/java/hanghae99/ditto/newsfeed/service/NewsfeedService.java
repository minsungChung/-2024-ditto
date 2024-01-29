package hanghae99.ditto.newsfeed.service;

import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.newsfeed.dto.request.NewsfeedRequest;
import hanghae99.ditto.newsfeed.dto.response.NewsfeedResponse;
import hanghae99.ditto.post.domain.Post;
import hanghae99.ditto.post.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsfeedService {
    Page<NewsfeedResponse> showNewsfeed(Pageable pageable);

    void createNewsfeed(NewsfeedRequest newsfeedRequest);

    Page<PostResponse> showPostNewsfeed(Pageable pageable);

    void createPostNewsfeed(Member feedMember, Post post);
}
