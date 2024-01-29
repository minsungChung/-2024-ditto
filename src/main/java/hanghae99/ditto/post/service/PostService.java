package hanghae99.ditto.post.service;

import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.post.dto.request.PostRequest;
import hanghae99.ditto.post.dto.response.PostLikeResponse;
import hanghae99.ditto.post.dto.response.PostResponse;

public interface PostService {
    PostResponse uploadPost(Member member, PostRequest postRequest);

    PostResponse getPost(Member member, Long postId);

    PostResponse updatePost(Member member, Long postId, PostRequest postRequest);

    PostResponse deletePost(Member member, Long postId);

    PostLikeResponse pushPostLike(Member member, Long postId);
}
