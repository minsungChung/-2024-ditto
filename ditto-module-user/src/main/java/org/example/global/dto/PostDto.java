package org.example.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    private Long postId;

    private Long writerId;

    private String title;

    private String content;

    private LocalDateTime createDate;

    private long likes;

    private long views;
}
