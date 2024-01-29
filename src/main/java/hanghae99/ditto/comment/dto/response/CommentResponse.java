package hanghae99.ditto.comment.dto.response;

import hanghae99.ditto.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public CommentResponse(Comment comment){
        this(comment.getId(), comment.getMember().getMemberName(), comment.getContent(), comment.getCreateDate(), comment.getLikes());
    }
}
