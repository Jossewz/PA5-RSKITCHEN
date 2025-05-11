package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.Platillo;
import com.example.rskitchen5.Repository.PlatilloRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platillos")
public class PlatilloController {

    @Autowired
    private PlatilloRep platilloRep;

    @GetMapping
    public List<Platillo> getAllPlatillos() {
        return platilloRep.findAll();
    }

    @PostMapping
    public Platillo crearPlatillo(@RequestBody Platillo platillo) {
        return platilloRep.save(platillo);
    }

    @PutMapping("/{id}")
    public Platillo updatePlatillo(@PathVariable Long id, @RequestBody Platillo platillo) {
        Platillo existingPlatillo = platilloRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Platillo no encontrado con id: " + id));
        existingPlatillo.setName(platillo.getName());
        existingPlatillo.setCant(platillo.getCant());
        existingPlatillo.setPrice(platillo.getPrice());
        existingPlatillo.setIngredients(platillo.getIngredients());  // Cambié el nombre aquí también
        return platilloRep.save(existingPlatillo);
    }

    @DeleteMapping("/{id}")
    public void deletePlatillo(@PathVariable Long id) {
        platilloRep.deleteById(id);
    }
}
