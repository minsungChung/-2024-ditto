package hanghae99.ditto.member.domain;

import hanghae99.ditto.global.entity.BaseEntity;
import hanghae99.ditto.global.entity.UsageStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "from_member_id", nullable = false)
    Member fromMember;

    @ManyToOne
    @JoinColumn(name = "to_member_id", nullable = false)
    Member toMember;

    @Builder
    public Follow(Member fromMember, Member toMember){
        this.fromMember = fromMember;
        this.toMember = toMember;
        this.status = UsageStatus.ACTIVE;
    }

    public void activateFollow(){
        this.status = UsageStatus.ACTIVE;
    }

    public void deleteFollow(){
        this.status = UsageStatus.DELETED;
    }

}
