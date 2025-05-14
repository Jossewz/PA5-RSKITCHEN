package com.example.rskitchen5.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/meseros")
@Controller
public class MeseroController {
    @GetMapping("")
    public String mostrarMeseros() {
        return "mesero";
    }
}
