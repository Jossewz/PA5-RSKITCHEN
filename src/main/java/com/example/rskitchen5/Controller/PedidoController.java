package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.*;
import com.example.rskitchen5.Repository.MesaRep;
import com.example.rskitchen5.Repository.PedidoRep;
import com.example.rskitchen5.Repository.PlatilloRep;
import com.example.rskitchen5.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRep pedidoRep;

    @Autowired
    private MesaRep mesaRep;

    @Autowired
    private PlatilloRep platilloRep;

    @Autowired
    private UserRep userRep;

    @GetMapping("")
    public String mostrarFormularioPedido(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("mesas", mesaRep.findAll());
        model.addAttribute("platillos", platilloRep.findAll());

        // Mostrar solo usuarios con rol MESERO
        List<User> meseros = userRep.findAll()
                .stream()
                .filter(user -> "MESERO".equalsIgnoreCase(user.getRol()))
                .toList();
        model.addAttribute("meseros", meseros);

        return "pedido"; // Mapea a pedido.html
    }

    @PostMapping("/crear")
    public String crearPedido(@ModelAttribute Pedido pedido,
                              @RequestParam(name = "platillosIds", required = false) List<String> platillosIds,
                              Model model) {
        try {
            // Validación mínima
            if (pedido.getMesaId() == null || pedido.getMeseroId() == null) {
                model.addAttribute("error", "Debe seleccionar una mesa y un mesero.");
                return "pedido";
            }

            List<ItemPedido> items = new ArrayList<>();
            double total = 0;

            if (platillosIds != null) {
                for (String id : platillosIds) {
                    Platillo platillo = platilloRep.findById(id).orElse(null);
                    if (platillo != null) {
                        ItemPedido item = new ItemPedido();
                        item.setNombre(platillo.getName());
                        item.setPrecio(platillo.getPrice());
                        item.setCantidad(1); // Por ahora cantidad fija
                        items.add(item);
                        total += platillo.getPrice();
                    }
                }
            }

            pedido.setItems(items);
            pedido.setFecha(LocalDateTime.now());
            pedido.setTotal(total);
            pedido.setPagado(false);

            pedidoRep.save(pedido);

            // Asociar pedido a la mesa
            mesaRep.findById(pedido.getMesaId()).ifPresent(mesa -> {
                List<String> pedidos = mesa.getPedidosId() != null ? mesa.getPedidosId() : new ArrayList<>();
                pedidos.add(pedido.getId());
                mesa.setPedidosId(pedidos);
                mesa.setOcupado(true);
                mesa.setMeseroId(pedido.getMeseroId());
                mesaRep.save(mesa);
            });

            return "redirect:/mesa";

        } catch (Exception e) {
            model.addAttribute("error", "Error al crear el pedido: " + e.getMessage());
            model.addAttribute("pedido", new Pedido());
            model.addAttribute("mesas", mesaRep.findAll());
            model.addAttribute("platillos", platilloRep.findAll());
            List<User> meseros = userRep.findAll()
                    .stream()
                    .filter(user -> "MESERO".equalsIgnoreCase(user.getRol()))
                    .toList();
            model.addAttribute("meseros", meseros);
            return "pedido";
        }
    }
}
