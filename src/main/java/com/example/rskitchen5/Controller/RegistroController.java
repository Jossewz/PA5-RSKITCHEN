package com.example.rskitchen5.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistroController {

    // Muestra el formulario de registro
    @GetMapping("/registrar")
    public String mostrarFormularioRegistro() {
        return "registro"; // este es el archivo registro.html
    }

    // Procesa los datos del formulario
    @PostMapping("/registrar")
    public String procesarRegistro(@RequestParam("nombre") String nombre,
                                   @RequestParam("correo") String correo,
                                   @RequestParam("password") String password,
                                   Model model) {

        // Aquí puedes guardar en la base de datos (lógica futura)
        System.out.println("Nombre: " + nombre);
        System.out.println("Correo: " + correo);
        System.out.println("Contraseña: " + password); // ¡Recuerda cifrarla después!

        // Agrega un mensaje de éxito (opcional para mostrar en la vista)
        model.addAttribute("mensaje", "Usuario registrado correctamente");

        return "redirect:/login"; // Redirige al login después del registro
    }
}
