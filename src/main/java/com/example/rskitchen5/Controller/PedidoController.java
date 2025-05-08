package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.Pedido;
import com.example.rskitchen5.Repository.PedidoRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRep pedidoRep;

    @GetMapping
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = pedidoRep.findAll();
        model.addAttribute("pedidos", pedidos);
        return "pedido/lista";
    }

    @GetMapping("/ver/{id}")
    public String verPedido(@PathVariable String id, Model model) {
        Long idLong = Long.parseLong(id);
        Pedido pedido = pedidoRep.findById(idLong).orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("pedido", pedido);
        return "pedido/ver";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("pedido", new Pedido());
        return "pedido/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute Pedido pedido) {
        pedido.setFecha(LocalDateTime.now());
        pedido.setPagado(false);
        pedidoRep.save(pedido);
        return "redirect:/pedidos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        pedidoRep.deleteById(idLong);
        return "redirect:/pedidos";
    }
}

