package org.example.service;

import org.assertj.core.api.Assertions;
import org.example.ActivityApplication;
import org.example.domain.FollowRepository;
import org.example.domain.Post;
import org.example.domain.PostLikeRepository;
import org.example.domain.PostRepository;
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
class PostServiceImplTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private FollowRepository followRepository;
    @AfterEach
    void tearDown() {
        postLikeRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
    }

    @DisplayName("동시성 테스트 - 낙관적 락")
    @Test
    void concurrencyOptimisticLockTest() throws InterruptedException {
        // given
        Post post = Post.builder()
                .title("낙관적 락 검사")
                .memberName("민성")
                .stockId(1L)
                .content("낙관적 락이 잘 먹힐까요~?")
                .memberId(1L)
                .build();
        Post savedPost = postRepository.save(post);

        // when
        int count = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 1; i <= count; i++){
            int finalI = i;
            executorService.execute(() -> {
                postService.pushPostLike((long) finalI, savedPost.getId());
                latch.countDown();
            });
        }
        latch.await();

        // then
        Post post1 = postRepository.findById(savedPost.getId()).get();
        Assertions.assertThat(post1.getLikes()).isEqualTo(count);
    }
}