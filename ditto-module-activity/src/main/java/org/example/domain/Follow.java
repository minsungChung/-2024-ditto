package org.example.domain;

import jakarta.persistence.Column;
import org.example.global.entity.BaseEntity;
import org.example.global.entity.UsageStatus;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseEntity {

    @Column(name = "from_member_id", nullable = false)
    Long fromMemberId;

    @Column(name = "to_member_id", nullable = false)
    Long toMemberId;

    @Builder
    public Follow(Long fromMember, Long toMemberId){
        this.fromMemberId = fromMember;
        this.toMemberId = toMemberId;
        this.status = UsageStatus.ACTIVE;
    }

    public void activateFollow(){
        this.status = UsageStatus.ACTIVE;
    }

    public void deleteFollow(){
        this.status = UsageStatus.DELETED;
    }

}
