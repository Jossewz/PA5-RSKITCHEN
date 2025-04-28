package com.example.rskitchen5.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistroController {

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro() {
        return "registro";
    }

    @PostMapping("/registrar")
    public String procesarRegistro(@RequestParam("nombre") String nombre,
                                   @RequestParam("correo") String correo,
                                   @RequestParam("password") String password,
                                   Model model) {

        System.out.println("Nombre: " + nombre);
        System.out.println("Correo: " + correo);
        System.out.println("Contraseña: " + password);

        model.addAttribute("mensaje", "Usuario registrado correctamente");

        return "redirect:/login";
    }
}
