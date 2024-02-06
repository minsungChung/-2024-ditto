package org.example.domain;

import org.example.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.global.entity.UsageStatus;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Newsfeed extends BaseEntity {

    @Column(name = "feed_member_id", nullable = false)
    private Long feedMemberId;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "message", nullable = false)
    private String message;

    @Builder
    private Newsfeed(Long feedMemberId, Long senderId, Long receiverId, String message) {
        this.feedMemberId = feedMemberId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.status = UsageStatus.ACTIVE;
    }
}
