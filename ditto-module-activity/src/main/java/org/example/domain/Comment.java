package org.example.domain;

import org.example.global.entity.BaseEntity;
import org.example.global.entity.UsageStatus;
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

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "likes", nullable = false)
    @ColumnDefault("0")
    private long likes;

    @Builder
    public Comment(Post post, Long memberId, String content){
        this.post = post;
        this.memberId = memberId;
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

    public void addLike(){
        this.likes += 1;
    }

    public void subLike(){
        this.likes -= 1;
    }
}
