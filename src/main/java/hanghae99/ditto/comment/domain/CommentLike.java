package hanghae99.ditto.comment.domain;

import hanghae99.ditto.global.entity.BaseEntity;
import hanghae99.ditto.global.entity.UsageStatus;
import hanghae99.ditto.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Builder
    public CommentLike(Member member, Comment comment){
        this.member = member;
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
