package org.example.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsfeedPostRequest {

    private Long feedMemberId;
    private Long postId;
    private Long writerMemberId;
}
