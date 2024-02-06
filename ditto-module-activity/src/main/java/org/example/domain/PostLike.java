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
public class PostLike extends BaseEntity {

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public PostLike(Long memberId, Post post){
        this.memberId = memberId;
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
