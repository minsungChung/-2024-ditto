package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.CommentLike;

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
