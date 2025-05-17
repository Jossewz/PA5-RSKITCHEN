package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.Factura;
import com.example.rskitchen5.Repository.FacturaRep;
import com.example.rskitchen5.Service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    private FacturaRep facturaRep;
    @Autowired
    private FacturaService facturaService;

    @GetMapping({ "", "/{id}" })
    public String mostrarFacturasOFactura(@PathVariable(required = false) String id, Model model) {
        List<Factura> facturas = facturaRep.findAll(Sort.by(Sort.Direction.DESC, "fecha"));
        model.addAttribute("facturas", facturas);

        if (id != null) {
            Optional<Factura> optionalFactura = facturaRep.findById(id);
            optionalFactura.ifPresent(factura -> model.addAttribute("factura", factura));
        }

        return "factura";
    }

    @GetMapping("/factura/imprimir/{id}")
    public String imprimirFactura(@PathVariable String id, Model model) {
        Factura factura = facturaService.getById(id);
        model.addAttribute("factura", factura);
        return "factura-imprimir";
    }

}