package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.factura;
import com.example.rskitchen5.Model.Pedido;
import com.example.rskitchen5.Repository.FacturaRep;
import com.example.rskitchen5.Repository.PedidoRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaRep facturaRep;

    @Autowired
    private PedidoRep pedidoRep;

    @GetMapping
    public String mostrarFacturas(Model model) {
        List<factura> facturas = facturaRep.findAll();
        model.addAttribute("facturas", facturas);
        return "factura";
    }

    @PostMapping("/crear")
    public String crearFactura(@RequestParam String pedidoId) {
        Pedido pedido = pedidoRep.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        double total = pedido.getItems()
                .stream()
                .mapToDouble(item -> item.getPrice() * item.getUnidades())
                .sum();

        factura factura = new factura();
        factura.setPedidoId(pedido.getId().toString());
        factura.setMesaId(pedido.getMesaId());
        factura.setMeseroId(pedido.getMeseroId());
        factura.setFecha(LocalDateTime.now());
        factura.setTotal(total);

        facturaRep.save(factura);

        return "redirect:/factura";
    }
}

