package com.example.rskitchen5.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogisController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "logis";
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "start";
    }

    @GetMapping("/start")
    public String showStartPage() {
        return "start";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        System.out.println("Login attempt for user: " + request.getParameter("username"));
        return "redirect:/home";
    }
}
