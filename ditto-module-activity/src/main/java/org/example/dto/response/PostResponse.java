package org.example.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.Post;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private long likes;
    private long views;

    public PostResponse(Post post){
        this(post.getId(), post.getTitle(),post.getContent(), post.getCreateDate(), post.getLikes(), post.getViews());
    }
}
