package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.Producto;
import com.example.rskitchen5.Repository.ProductoRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/producto")
public class ProductoController {


    @Autowired
    private ProductoRep productoRep;

    @GetMapping
    public String getAllProductos(Model model) {
        List<Producto>productos=productoRep.findAll();
        model.addAttribute("productos", productoRep.findAll());
        return "productos";

    }


    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRep.save(producto);
    }

    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable String id, @RequestBody Producto producto) {
        Producto existingProducto = productoRep.findById(id).orElseThrow();
        existingProducto.setName(producto.getName());
        existingProducto.setCant(producto.getCant());
        existingProducto.setPrice(producto.getPrice());
        existingProducto.setStock(producto.getStock());
        return productoRep.save(existingProducto);
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable String id) {
        productoRep.deleteById(id);
    }
}