package com.example.rskitchen5.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {

    @GetMapping("/")
    public String landing() {
        return "landing";
    }

    @GetMapping("/landing")
    public String redirigirLanding() {
        return "redirect:/landing";
    }


}
