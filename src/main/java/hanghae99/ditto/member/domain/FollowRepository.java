package hanghae99.ditto.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFromMemberIdAndToMemberId(Long fromMemberId, Long toMemberId);
}
