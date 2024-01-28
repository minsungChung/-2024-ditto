package hanghae99.ditto.newsfeed.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsfeedRepository extends JpaRepository<Newsfeed, Long> {

    List<Newsfeed> findAllByFeedMemberId(Long memberId);
}
