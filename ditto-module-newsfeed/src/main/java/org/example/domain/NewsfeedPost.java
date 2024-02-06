package org.example.domain;
import jakarta.persistence.Column;
import org.example.global.entity.BaseEntity;


import jakarta.persistence.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.global.entity.UsageStatus;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsfeedPost extends BaseEntity {

    @Column(name = "feed_member_id", nullable = false)
    private Long feedMemberId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "writer_member_id", nullable = false)
    private Long writerMemberId;

    @Builder
    public NewsfeedPost(Long feedMemberId, Long postId, Long writerMemberId) {
        this.feedMemberId = feedMemberId;
        this.postId = postId;
        this.writerMemberId = writerMemberId;
        this.status = UsageStatus.ACTIVE;
    }
}
