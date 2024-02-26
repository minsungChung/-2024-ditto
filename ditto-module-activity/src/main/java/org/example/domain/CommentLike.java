package org.example.domain;

import jakarta.persistence.Column;
import org.example.global.entity.BaseEntity;
import org.example.global.entity.UsageStatus;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends BaseEntity {
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Builder
    public CommentLike(Long memberId, Long commentId){
        this.memberId = memberId;
        this.commentId = commentId;
        this.status = UsageStatus.ACTIVE;
    }

    public void deleteCommentLike(){
        this.status = UsageStatus.DELETED;
    }

    public void pushCommentLike(){
        this.status = UsageStatus.ACTIVE;
    }
}
