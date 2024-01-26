package hanghae99.ditto.post.service;

import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.post.domain.Post;
import hanghae99.ditto.post.domain.PostRepository;
import hanghae99.ditto.post.dto.request.PostRequest;
import hanghae99.ditto.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse uploadPost(PostRequest postRequest) {
        Post post = Post.builder()
                .member((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();
        postRepository.save(post);

        return new PostResponse(post);
    }
}
