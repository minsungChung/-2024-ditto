package hanghae99.ditto.member.domain;

import hanghae99.ditto.global.entity.BaseEntity;
import hanghae99.ditto.global.entity.UsageStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "member_name", nullable = false)
    private String memberName;

    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @Column(name = "bio")
    private String bio;

    @Column(name = "last_login", nullable = false)
    private LocalDateTime lastLogin;

    public Member(String email, String password, String memberName, String profileImage, String bio, LocalDateTime lastLogin){
        this.email = email;
        this.password = password;
        this.memberName = memberName;
        this.profileImage = profileImage;
        this.bio = bio;
        this.lastLogin = lastLogin;
        this.status = UsageStatus.NOT_EMAIL_AUTH;
    }

    public void verifiedWithEmail(){
        this.status = UsageStatus.ACTIVE;
    }
}
