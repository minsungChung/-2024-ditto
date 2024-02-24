package org.example.domain;

import jakarta.persistence.Version;
import org.example.global.entity.BaseEntity;
import org.example.global.entity.UsageStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Column(name="member_id", nullable = false)
    private Long memberId;

    @Column(name = "member_name", nullable = false)
    private String memberName;

    @Column(name = "stock_id", nullable = false)
    private Long stockId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "likes", nullable = false)
    @ColumnDefault("0")
    private long likes;

    @Column(name = "views", nullable = false)
    @ColumnDefault("0")
    private long views;

    @Version
    private Long version;

    @Builder
    public Post(Long memberId, String memberName, Long stockId, String title, String content){
        this.memberId = memberId;
        this.memberName = memberName;
        this.stockId = stockId;
        this.title = title;
        this.content = content;
        this.status = UsageStatus.ACTIVE;
    }

    public void updatePost(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void deletePost(){
        this.status = UsageStatus.DELETED;
    }

    public void addView(){ this.views += 1;}

    public void addLike() {this.likes += 1;}
    public void subLike() {this.likes -= 1;}

}
