package org.example.service;

import org.example.dto.request.PostRequest;
import org.example.dto.response.PostLikeResponse;
import org.example.dto.response.PostResponse;
import org.example.dto.response.PostSimpleRes;
import org.example.global.dto.PostDto;

public interface PostService {
    PostResponse uploadPost(Long memberId, PostRequest postRequest);

    PostResponse getPost(Long memberId, Long postId);

    PostSimpleRes updatePost(Long memberId, Long postId, PostRequest postRequest);

    PostSimpleRes deletePost(Long memberId, Long postId);

    PostLikeResponse pushPostLike(Long memberId, Long postId);

    PostDto getOnePost(Long postId);
}
