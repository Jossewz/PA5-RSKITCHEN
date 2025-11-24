package com.example.rskitchen5.Flutter;

import com.example.rskitchen5.Model.Platillo;
import com.example.rskitchen5.Repository.PlatilloRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/platillos")
public class PlatilloRestController {

    @Autowired
    private PlatilloRep rep;

    @GetMapping
    public List<Platillo> getAll() {
        return rep.findAll();
    }

    @PostMapping
    public Platillo create(@RequestBody Platillo p) {
        return rep.save(p);
    }

    @PutMapping("/{id}")
    public Platillo update(@PathVariable String id, @RequestBody Platillo p) {
        p.setId(id);
        return rep.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        rep.deleteById(id);
    }
}
