package hanghae99.ditto.post.service;

import hanghae99.ditto.post.dto.request.PostRequest;
import hanghae99.ditto.post.dto.response.PostResponse;

public interface PostService {
    PostResponse uploadPost(PostRequest postRequest);
}
