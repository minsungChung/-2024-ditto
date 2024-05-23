package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Post;
import org.example.service.MyService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class MyController {

    private final MyService myService;

    @GetMapping
    public String findPosts(Model model){
        List<Post> posts = myService.findPosts();

        model.addAttribute("posts", posts);

        return "html/post/list.html";
    }

    @GetMapping("/{id}")
    public String getPostDetail(@PathVariable Long id, Model model) {
        Post post = myService.findPostById(id);
        model.addAttribute("post", post);
        return "html/post/detail.html";  // 게시글 상세 페이지 템플릿 경로
    }
}
