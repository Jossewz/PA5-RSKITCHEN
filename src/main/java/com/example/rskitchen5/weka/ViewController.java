package com.example.rskitchen5.weka;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/weka/test")
    public String mostrarVistaWeka() {

        return "datos/weka_test";
    }
}