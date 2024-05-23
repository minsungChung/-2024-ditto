package org.example.service;

import org.example.dto.request.PostRequest;
import org.example.dto.response.PostLikeResponse;
import org.example.dto.response.PostResponse;
import org.example.dto.response.PostSimpleRes;
import org.example.global.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostResponse uploadPost(String email, PostRequest postRequest);

    PostResponse getPost(String email, Long postId);

    PostSimpleRes updatePost(String email, Long postId, PostRequest postRequest);

    PostSimpleRes deletePost(String email, Long postId);

    PostLikeResponse pushPostLike(String email, Long postId);

    PostDto getOnePost(Long postId);

    Page<PostDto> getPostsByStockId(Long stockId, Pageable pageable);

    Page<PostSimpleRes> getPostsByTitle(String title, Pageable pageable);

    Page<PostSimpleRes> getPostsByContent(String content, Pageable pageable);

    Page<PostSimpleRes> getPostsByMemberName(String memberName, Pageable pageable);
}
