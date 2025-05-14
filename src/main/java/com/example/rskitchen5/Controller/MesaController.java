package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.Mesa;
import com.example.rskitchen5.Model.Pedido;
import com.example.rskitchen5.Model.User;
import com.example.rskitchen5.Repository.MesaRep;
import com.example.rskitchen5.Repository.PedidoRep;
import com.example.rskitchen5.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/mesa")
public class MesaController {

    private final MesaRep mesaRepository;
    private final PedidoRep pedidoRep;
    private final UserRep userRep;

    @Autowired
    public MesaController(MesaRep mesaRepository, PedidoRep pedidoRep, UserRep userRep) {
        this.mesaRepository = mesaRepository;
        this.pedidoRep = pedidoRep;
        this.userRep = userRep;
    }

    @GetMapping("")
    public String mostrarVistaMesa(Model model) {
        List<Mesa> mesas = mesaRepository.findAll();
        Map<String, List<Pedido>> pedidosPorMesa = new HashMap<>();
        Map<String, String> nombresMeseros = new HashMap<>();

        for (Mesa mesa : mesas) {
            List<Pedido> pedidos = new ArrayList<>();
            if (mesa.getPedidosId() != null) {
                for (String pedidoId : mesa.getPedidosId()) {
                    pedidoRep.findById(pedidoId).ifPresent(pedidos::add);
                }
            }

            pedidosPorMesa.put(mesa.getId(), pedidos);

            if (mesa.getMeseroId() != null) {
                userRep.findById(mesa.getMeseroId()).ifPresent(mesero -> {
                    nombresMeseros.put(mesa.getId(), mesero.getName());
                });
            }
        }

        model.addAttribute("listar", mesas);
        model.addAttribute("nuevaMesa", new Mesa());
        model.addAttribute("pedidosPorMesa", pedidosPorMesa);
        model.addAttribute("nombresMeseros", nombresMeseros);
        return "mesa";
    }

    @PostMapping("/crear")
    public String crearMesa(@ModelAttribute Mesa mesa, Model model) {
        try {
            mesaRepository.save(mesa);
            model.addAttribute("mensajeExito", "Mesa creada exitosamente");
        } catch (Exception e) {
            model.addAttribute("mensajeError", "Error al crear la mesa: " + e.getMessage());
        }
        return "redirect:/mesa";
    }

    @GetMapping("/{id}")
    public String mostrarMesaDetalle(@PathVariable String id, Model model) {
        mesaRepository.findById(id).ifPresent(mesa -> {
            model.addAttribute("mesaDetalle", mesa);

            List<Pedido> pedidos = new ArrayList<>();
            if (mesa.getPedidosId() != null) {
                for (String pedidoId : mesa.getPedidosId()) {
                    pedidoRep.findById(pedidoId).ifPresent(pedidos::add);
                }
            }

            model.addAttribute("pedidos", pedidos);

            if (mesa.getMeseroId() != null) {
                userRep.findById(mesa.getMeseroId()).ifPresent(mesero -> {
                    model.addAttribute("nombreMesero", mesero.getName());
                });
            }
        });

        model.addAttribute("listar", mesaRepository.findAll());
        return "mesa";
    }
}
