package org.example.service;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.example.domain.Post;
import org.example.domain.PostRepository;
import org.example.global.exception.NoSuchPostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyService {

    private static final Logger log = LoggerFactory.getLogger(MyService.class);
    private final PostRepository postRepository;

    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(NoSuchPostException::new);
    }
}
