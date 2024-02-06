package org.example.domain;

import org.example.global.entity.UsageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberAuthenticationCodeRepository extends JpaRepository<MemberAuthenticationCodeEntity, Long> {

    // 이메일로 end_date가 지금 이후고, status가 active인거 찾아오기
    // 즉, 아직 유효한 인증코드 불러오기
    Optional<MemberAuthenticationCodeEntity> findByEmailAndEndDateAfterAndStatusEquals(String email, LocalDateTime currentDateTime, UsageStatus usageStatus);

    Optional<MemberAuthenticationCodeEntity> findByEmailAndIsAuthenticatedIsTrue(String email);
}
