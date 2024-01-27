package hanghae99.ditto.post.domain;

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
public class PostLike extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public PostLike(Member member, Post post){
        this.member = member;
        this.post = post;
        this.status = UsageStatus.ACTIVE;
        post.addLike();
    }

    public void deletePostLike(){
        this.status = UsageStatus.DELETED;
        post.subLike();
    }

    public void pushPostLike(){
        this.status = UsageStatus.ACTIVE;
        post.addLike();
    }
}
