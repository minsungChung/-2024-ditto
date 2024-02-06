package org.example.domain;

import jakarta.persistence.Column;
import org.example.global.entity.BaseEntity;
import org.example.global.entity.UsageStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends BaseEntity {
    @Column(name = "=member_id", nullable = false)
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Builder
    public CommentLike(Long memberId, Comment comment){
        this.memberId = memberId;
        this.comment = comment;
        this.status = UsageStatus.ACTIVE;
        comment.addLike();
    }

    public void deleteCommentLike(){
        this.status = UsageStatus.DELETED;
        comment.subLike();
    }

    public void pushCommentLike(){
        this.status = UsageStatus.ACTIVE;
        comment.addLike();
    }
}
