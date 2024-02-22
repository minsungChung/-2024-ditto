package org.example.domain;

import org.example.global.entity.BaseEntity;
import org.example.global.entity.UsageStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Entity
@NoArgsConstructor
public class MemberAuthenticationCodeEntity extends BaseEntity {

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "is_authenticated", nullable = false)
    private boolean isAuthenticated;

    public MemberAuthenticationCodeEntity(String email, String code){
        this.email = email;
        this.code = code;
        this.endDate = LocalDateTime.now().plus(5, ChronoUnit.MINUTES);
        this.status = UsageStatus.ACTIVE;
        this.isAuthenticated = false;
    }

    public void deleteCode(){
        this.status = UsageStatus.DELETED;
    }

    public void authenticateEmail() { this.isAuthenticated = true;}

}
