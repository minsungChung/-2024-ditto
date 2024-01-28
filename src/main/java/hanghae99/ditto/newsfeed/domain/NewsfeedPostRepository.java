package hanghae99.ditto.newsfeed.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsfeedPostRepository extends JpaRepository<NewsfeedPost, Long> {

    List<NewsfeedPost> findAllByFeedMemberId(Long memberId);
}
