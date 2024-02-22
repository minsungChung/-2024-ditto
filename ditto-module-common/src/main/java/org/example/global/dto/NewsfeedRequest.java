package org.example.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsfeedRequest {
    private Long feedMemberId;
    private Long senderId;
    private Long receiverId;
    private String type;
    private String postTitle;
}
