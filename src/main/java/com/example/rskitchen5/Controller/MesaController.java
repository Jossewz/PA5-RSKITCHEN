package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.Mesa;
import com.example.rskitchen5.Repository.MesaRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/mesa")
public class MesaController {

    private final MesaRep mesaRepository;

    @Autowired
    public MesaController(MesaRep mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    // Mostrar vista con lista de mesas y formulario de creación
    @GetMapping("")
    public String mostrarVistaMesa(Model model) {
        model.addAttribute("listar", mesaRepository.findAll());
        model.addAttribute("nuevaMesa", new Mesa());  // Para mostrar el formulario de creación
        return "mesa";  // Nombre de la vista, debe ser mesa.html
    }

    // Crear nueva mesa
    @PostMapping("/crear")
    public String crearMesa(@ModelAttribute Mesa mesa, Model model) {
        try {
            mesaRepository.save(mesa);  // Guardar la nueva mesa
            model.addAttribute("listar", mesaRepository.findAll());  // Actualizar lista de mesas
            model.addAttribute("nuevaMesa", new Mesa());  // Limpiar formulario
            model.addAttribute("mensajeExito", "Mesa creada exitosamente");
        } catch (Exception e) {
            model.addAttribute("mensajeError", "Error al crear la mesa: " + e.getMessage());
        }
        return "mesa";  // Volver a la misma vista
    }

    // Mostrar detalles de una mesa (opcional)
    @GetMapping("/{id}")
    public String mostrarMesaDetalle(@PathVariable String id, Model model) {
        mesaRepository.findById(id).ifPresent(mesa -> {
            model.addAttribute("mesaDetalle", mesa);
        });
        model.addAttribute("listar", mesaRepository.findAll());
        return "mesa";  // Volver a la misma vista
    }
}
