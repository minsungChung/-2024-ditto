package hanghae99.ditto.member.domain;

import hanghae99.ditto.global.entity.BaseEntity;
import hanghae99.ditto.global.entity.UsageStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements UserDetails {

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

    @Builder
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() { return password;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
