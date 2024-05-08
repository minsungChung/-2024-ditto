package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.MyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MyController {

    private final MyService myService;

    @GetMapping("/members")
    public String list(Model model){
        List<Map<String, Object>> members = myService.findMembers();

        model.addAttribute("memberList", members);

        return "html/member/list.html";
    }
}
