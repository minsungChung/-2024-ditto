package org.example.domain;

import jakarta.persistence.*;
import org.example.global.entity.BaseEntity;
import org.example.global.entity.UsageStatus;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike extends BaseEntity {

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Builder
    public PostLike(Long memberId, Long postId){
        this.memberId = memberId;
        this.postId = postId;
        this.status = UsageStatus.ACTIVE;
    }

    public void deletePostLike(){
        this.status = UsageStatus.DELETED;
    }

    public void pushPostLike(){
        this.status = UsageStatus.ACTIVE;
    }
}
