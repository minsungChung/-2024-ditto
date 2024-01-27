package hanghae99.ditto.post.domain;

import hanghae99.ditto.global.entity.BaseEntity;
import hanghae99.ditto.global.entity.UsageStatus;
import hanghae99.ditto.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

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

    @Builder
    public Post(Member member, String title, String content){
        this.member = member;
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

}
