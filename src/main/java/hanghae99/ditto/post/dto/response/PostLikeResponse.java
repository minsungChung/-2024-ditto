package hanghae99.ditto.post.dto.response;

import hanghae99.ditto.post.domain.PostLike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeResponse {
    private Long postLikeId;
    private String status;

    public PostLikeResponse(PostLike postLike){
        this(postLike.getId(), postLike.getStatus().toString());
    }
}
