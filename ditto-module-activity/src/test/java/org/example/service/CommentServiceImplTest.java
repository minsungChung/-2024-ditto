package org.example.service;

import org.assertj.core.api.Assertions;
import org.example.ActivityApplication;
import org.example.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = ActivityApplication.class)
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private FollowRepository followRepository;
    @AfterEach
    void tearDown() {
        commentLikeRepository.deleteAllInBatch();
        commentRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
    }

    @DisplayName("동시성 테스트 - 낙관적 락")
    @Test
    void concurrencyOptimisticLockTest() throws InterruptedException {
        // given
        Post post = Post.builder()
                .title("1")
                .memberName("민성")
                .stockId(1L)
                .content("11")
                .memberId(1L)
                .build();
        Post savedPost = postRepository.save(post);
        Comment comment = Comment.builder()
                .content("냐하")
                .memberId(1L)
                .postId(savedPost.getId())
                .build();
        Comment savedComment = commentRepository.save(comment);

        // when
        int count = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 1; i <= count; i++){
            int finalI = i;
            executorService.execute(() -> {
                commentService.pushCommentLike((long) finalI, savedPost.getId(), savedComment.getId());
                latch.countDown();
            });
        }
        latch.await();

        // then
        Comment comment1 = commentRepository.findById(savedComment.getId()).get();
        Assertions.assertThat(comment1.getLikes()).isEqualTo(count);
    }

}