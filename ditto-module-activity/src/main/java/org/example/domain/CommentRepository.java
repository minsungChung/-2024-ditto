package org.example.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long postId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select c from Comment c where c.id = :id")
    Optional<Comment> findByIdWithLock(@Param("id") Long id);
}
