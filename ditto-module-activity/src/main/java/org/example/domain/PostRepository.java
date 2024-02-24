package org.example.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select p from Post p where p.id = :id")
    Optional<Post> findByIdWithLock(@Param("id") Long id);

//    @Modifying(clearAutomatically = true, flushAutomatically = true)
//    @Query(value = "update Post p set p.likes = p.likes + 1 where p.id = :id")
//    void increaseLikeCount(@Param("id") Long id);
//
//    @Modifying(clearAutomatically = true, flushAutomatically = true)
//    @Query(value = "update Post p set p.likes = p.likes - 1 where p.id = :id")
//    void decreaseLikeCount(@Param("id") Long id);

    Page<Post> findAllByStockId(Long stockId, Pageable pageable);

    Page<Post> findAllByTitleContains(String title, Pageable pageable);

    Page<Post> findAllByContentContains(String content, Pageable pageable);

    Page<Post> findAllByMemberName(String memberName, Pageable pageable);
}
