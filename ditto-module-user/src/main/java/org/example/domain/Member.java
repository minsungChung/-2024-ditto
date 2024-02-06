package org.example.domain;

import org.example.global.entity.BaseEntity;
import org.example.global.entity.UsageStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


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

    @Builder
    public Member(String email, String password, String memberName, String profileImage, String bio, LocalDateTime lastLogin){
        this.email = email;
        this.password = password;
        this.memberName = memberName;
        this.profileImage = profileImage;
        this.bio = bio;
        this.lastLogin = lastLogin;
        this.status = UsageStatus.ACTIVE;
    }

    public void updateLastLogin() { this.lastLogin = LocalDateTime.now(); }

    public void updateMemberExtraInfo(String memberName, String profileImage, String bio){
        this.memberName = memberName;
        this.profileImage = profileImage;
        this.bio = bio;
    }

    public void updateMemberPassword(String password){
        this.password = password;
    }


}
