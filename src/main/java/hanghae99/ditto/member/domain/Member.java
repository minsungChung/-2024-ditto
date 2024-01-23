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
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");
    private static final int MAX_NAME_LENGTH = 25;

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

        validateEmail(email);
        validateMemberName(memberName);

        this.email = email;
        this.password = password;
        this.memberName = memberName;
        this.profileImage = profileImage;
        this.bio = bio;
        this.lastLogin = lastLogin;
        this.status = UsageStatus.ACTIVE;
    }

    private void validateMemberName(String memberName) {
        if (memberName.isEmpty() || memberName.length() > MAX_NAME_LENGTH){
            throw new IllegalArgumentException(String.format("이름은 1자 이상 %d자 이하여야 합니다.", MAX_NAME_LENGTH));
        }
    }

    private void validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }
}
