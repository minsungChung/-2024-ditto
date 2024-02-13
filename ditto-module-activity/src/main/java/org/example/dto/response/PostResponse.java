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
    private String stockName;
    private String memberName;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private long likes;
    private long views;

    public PostResponse(Post post, String stockName, String memberName){
        this(post.getId(),stockName, memberName, post.getTitle(),post.getContent(), post.getCreateDate(), post.getLikes(), post.getViews());
    }
}
