package hanghae99.ditto.newsfeed.domain;

import hanghae99.ditto.global.entity.BaseEntity;
import hanghae99.ditto.global.entity.UsageStatus;
import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.post.domain.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsfeedPost extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "feed_member_id", nullable = false)
    private Member feedMember;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public NewsfeedPost(Member feedMember, Post post){
        this.feedMember = feedMember;
        this.post = post;
        this.status = UsageStatus.ACTIVE;
    }
}
