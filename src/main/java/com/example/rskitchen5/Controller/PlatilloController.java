package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.Platillo;
import com.example.rskitchen5.Repository.PlatilloRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/platillo")
public class PlatilloController {

    @Autowired
    private PlatilloRep platilloRep;

    @GetMapping
    public String getAllPlatillos(Model model) {
        List<Platillo> platillos = platilloRep.findAll();
        model.addAttribute("platillos", platillos);
        return "platillos";
    }

    @PostMapping
    public String crearPlatillo(@ModelAttribute Platillo platillo) {
        platilloRep.save(platillo);
        return "redirect:/platillos";
    }

    @PutMapping("/{id}")
    public String updatePlatillo(@PathVariable String id, @ModelAttribute Platillo platillo) {
        Platillo existingPlatillo = platilloRep.findById(id).orElseThrow();
        existingPlatillo.setName(platillo.getName());
        existingPlatillo.setCant(platillo.getCant());
        existingPlatillo.setPrice(platillo.getPrice());
        existingPlatillo.setIngredients(platillo.getIngredients());
        platilloRep.save(existingPlatillo);
        return "redirect:/platillos";
    }

    @DeleteMapping("/{id}")
    public String deletePlatillo(@PathVariable String id) {
        platilloRep.deleteById(id);
        return "redirect:/platillos";
    }
}
