package org.example.service;

import org.example.global.dto.NewsfeedPostRequest;
import org.example.global.dto.NewsfeedRequest;
import org.example.dto.response.NewsfeedPostResponse;
import org.example.dto.response.NewsfeedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsfeedService {
    Page<NewsfeedResponse> showNewsfeed(Long memberId, Pageable pageable);

    String createNewsfeed(NewsfeedRequest newsfeedRequest);

    Page<NewsfeedPostResponse> showPostNewsfeed(Long memberId, Pageable pageable);

    String createPostNewsfeed(NewsfeedPostRequest newsfeedPostRequest);
}
