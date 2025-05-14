package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.Mesa;
import com.example.rskitchen5.Repository.MesaRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/mesa")
public class MesaController {

    private final MesaRep mesaRepository;

    @Autowired
    public MesaController(MesaRep mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @GetMapping("")
    public String mostrarVistaMesa(Model model) {
        model.addAttribute("listar", mesaRepository.findAll());
        model.addAttribute("nuevaMesa", new Mesa());
        return "mesa";
    }

    @PostMapping("/crear")
    public String crearMesa(@ModelAttribute Mesa mesa, Model model) {
        try {
            mesaRepository.save(mesa);  // Guardar la nueva mesa
            model.addAttribute("listar", mesaRepository.findAll());
            model.addAttribute("nuevaMesa", new Mesa());
            model.addAttribute("mensajeExito", "Mesa creada exitosamente");
        } catch (Exception e) {
            model.addAttribute("mensajeError", "Error al crear la mesa: " + e.getMessage());
        }
        return "mesa";
    }

    @GetMapping("/{id}")
    public String mostrarMesaDetalle(@PathVariable String id, Model model) {
        mesaRepository.findById(id).ifPresent(mesa -> {
            model.addAttribute("mesaDetalle", mesa);
        });
        model.addAttribute("listar", mesaRepository.findAll());
        return "mesa";
    }
}
