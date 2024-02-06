package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.PostLike;

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
