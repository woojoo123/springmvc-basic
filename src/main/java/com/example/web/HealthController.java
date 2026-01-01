package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {

    @GetMapping("/health")
    public String health(Model model) {
        model.addAttribute("status", "OK");
        return "health"; // /WEB-INF/views/health.jsp
    }
}