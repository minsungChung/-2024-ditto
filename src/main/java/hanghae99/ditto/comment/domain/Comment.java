package hanghae99.ditto.comment.domain;

import hanghae99.ditto.global.entity.BaseEntity;
import hanghae99.ditto.global.entity.UsageStatus;
import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.post.domain.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "likes", nullable = false)
    @ColumnDefault("0")
    private long likes;

    @Builder
    public Comment(Post post, Member member, String content){
        this.post = post;
        this.member = member;
        this.content = content;
        this.status = UsageStatus.ACTIVE;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void deleteComment(){
        this.content = "삭제된 댓글입니다.";
        this.status = UsageStatus.DELETED;
    }
}
