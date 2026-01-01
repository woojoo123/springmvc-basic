package com.example.web;

import com.example.repo.PostStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostsController {

    // GET /posts/page -> 목록 JSP
    @GetMapping("/page")
    public String page(Model model) {
        var items = PostStore.findAll();
        model.addAttribute("items", items);
        model.addAttribute("count", items.size());
        return "posts"; // /WEB-INF/views/posts.jsp
    }

    // GET /posts/new -> 작성 폼 JSP
    @GetMapping("/new")
    public String newForm(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("error", "1".equals(error));
        return "new"; // /WEB-INF/views/new.jsp
    }

    // POST /posts/page -> 저장 후 redirect(PRG)
    @PostMapping("/page")
    public String create(@RequestParam String title,
                         @RequestParam String content) {

        if (title == null || title.trim().isEmpty() ||
            content == null || content.trim().isEmpty()) {
            return "redirect:/posts/new?error=1";
        }

        PostStore.add(title.trim(), content.trim());

        // PRG: POST 후 redirect -> 새로고침 중복 제출 방지
        return "redirect:/posts/page";
    }
}