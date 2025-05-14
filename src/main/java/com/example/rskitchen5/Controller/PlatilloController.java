package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.Platillo;
import com.example.rskitchen5.Repository.PlatilloRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/platillos") // Cambiado a plural
public class PlatilloController {

    @Autowired
    private PlatilloRep platilloRep;

    @GetMapping("")
    public String getAllPlatillos(Model model) {
        List<Platillo> platillos = platilloRep.findAll();
        model.addAttribute("platillos", platillos);
        return "platillos"; // El nombre del archivo HTML
    }

    @PostMapping("")
    public String crearPlatillo(@ModelAttribute Platillo platillo) {
        platilloRep.save(platillo);
        return "redirect:/platillos"; // Redirección corregida
    }

    @PostMapping("/{id}/actualizar") // Para manejar desde formulario HTML
    public String updatePlatillo(@PathVariable String id, @ModelAttribute Platillo platillo) {
        Platillo existingPlatillo = platilloRep.findById(id).orElseThrow();
        existingPlatillo.setName(platillo.getName());
        existingPlatillo.setCant(platillo.getCant());
        existingPlatillo.setPrice(platillo.getPrice());
        existingPlatillo.setIngredients(platillo.getIngredients());
        platilloRep.save(existingPlatillo);
        return "redirect:/platillos";
    }

    @GetMapping("/{id}/eliminar") // Para hacerlo compatible con enlaces
    public String deletePlatillo(@PathVariable String id) {
        platilloRep.deleteById(id);
        return "redirect:/platillos";
    }
}
