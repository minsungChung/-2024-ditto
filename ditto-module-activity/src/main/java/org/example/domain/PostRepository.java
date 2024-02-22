package org.example.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByStockId(Long stockId, Pageable pageable);

    Page<Post> findAllByTitleContains(String title, Pageable pageable);

    Page<Post> findAllByContentContains(String content, Pageable pageable);

    Page<Post> findAllByMemberName(String memberName, Pageable pageable);
}
