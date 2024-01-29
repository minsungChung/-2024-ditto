package hanghae99.ditto.newsfeed.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface NewsfeedPostRepository extends JpaRepository<NewsfeedPost, Long> {

    @Query("SELECT n FROM NewsfeedPost n WHERE n.post.status='ACTIVE'")
    Page<NewsfeedPost> findAllByFeedMemberId(Long memberId, Pageable pageable);
}
