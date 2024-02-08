package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String memberName;
    private String content;
    private LocalDateTime createDate;
    private long likes;

    public CommentResponse(String memberName, Comment comment){
        this(comment.getId(), memberName, comment.getContent(), comment.getCreateDate(), comment.getLikes());
    }
}
