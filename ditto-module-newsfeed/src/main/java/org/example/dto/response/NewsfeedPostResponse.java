package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.global.dto.PostDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsfeedPostResponse {
    private Long postId;

    private Long writerId;

    private String writerName;

    private String title;

    private String content;

    private LocalDateTime createDate;

    private long likes;

    private long views;

    public NewsfeedPostResponse(PostDto postDto, Long writerId, String writerName){
        this(postDto.getPostId(), writerId, writerName, postDto.getTitle(), postDto.getContent(), postDto.getCreateDate(), postDto.getLikes(), postDto.getViews());
    }

}
