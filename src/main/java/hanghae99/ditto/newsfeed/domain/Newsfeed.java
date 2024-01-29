package hanghae99.ditto.newsfeed.domain;

import hanghae99.ditto.global.entity.BaseEntity;
import hanghae99.ditto.global.entity.UsageStatus;
import hanghae99.ditto.member.domain.Member;
import jakarta.persistence.Column;
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
public class Newsfeed extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "feed_member_id", nullable = false)
    private Member feedMember;

    @ManyToOne
    @JoinColumn(name = "sender_member_id", nullable = false)
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiver_member_id", nullable = false)
    private Member receiver;

    @Column(name = "message", nullable = false)
    private String message;

    @Builder
    public Newsfeed(Member feedMember, Member sender, Member receiver, String message){
        this.feedMember = feedMember;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.status = UsageStatus.ACTIVE;
    }
}
