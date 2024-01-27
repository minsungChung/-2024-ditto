package hanghae99.ditto.comment.dto.response;

import hanghae99.ditto.comment.domain.CommentLike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeResponse {
    private Long commentLikeId;
    private String status;

    public CommentLikeResponse(CommentLike commentLike){
        this(commentLike.getId(), commentLike.getStatus().toString());
    }
}
