package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.*;
import com.example.rskitchen5.Repository.MesaRep;
import com.example.rskitchen5.Repository.PedidoRep;
import com.example.rskitchen5.Repository.PlatilloRep;
import com.example.rskitchen5.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @GetMapping
    public String mostrarFormularioPedido(Model model) {
        try {
            model.addAttribute("pedido", new Pedido());
            model.addAttribute("mesas", mesaRep.findByOcupadoFalse());
            model.addAttribute("platillos", platilloRep.findAll());

            List<User> meseros = userRep.findAll().stream()
                    .filter(user -> user.getRol() != null &&
                            ("MESERO".equalsIgnoreCase(user.getRol()) ||
                                    "ROLE_MESERO".equalsIgnoreCase(user.getRol())))
                    .collect(Collectors.toList());
            model.addAttribute("meseros", meseros);

            model.addAttribute("pedidosRecientes", getPedidosConDetalles());
            return "pedido";

        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el formulario de pedidos");
            return "error";
        }
    }

    @PostMapping("/crear")
    public String crearPedido(@ModelAttribute("pedido") Pedido pedido,
                              BindingResult result,
                              @RequestParam(name = "platillosIds", required = false) List<String> platillosIds,
                              @RequestParam(name = "cantidades", required = false) List<String> cantidades,
                              Model model) {

        // Validaciones básicas
        if (result.hasErrors() || pedido.getMesaId() == null || pedido.getMeseroId() == null ||
                platillosIds == null || platillosIds.isEmpty()) {
            model.addAttribute("error", validarDatos(pedido, platillosIds));
            return cargarDatosModelo(model);
        }

        try {
            Mesa mesa = mesaRep.findById(pedido.getMesaId())
                    .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
            User mesero = userRep.findById(pedido.getMeseroId())
                    .orElseThrow(() -> new IllegalArgumentException("Mesero no encontrado"));

            Pedido nuevoPedido = crearNuevoPedido(pedido, mesa, mesero, platillosIds, cantidades);
            Pedido pedidoGuardado = pedidoRep.save(nuevoPedido);
            actualizarMesa(mesa, pedidoGuardado, mesero);

            return "redirect:/pedidos?exito=Pedido+creado+correctamente";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return cargarDatosModelo(model);
        } catch (Exception e) {
            model.addAttribute("error", "Error inesperado al crear el pedido");
            return cargarDatosModelo(model);
        }
    }

    // Métodos auxiliares
    private List<Pedido> getPedidosConDetalles() {
        return pedidoRep.findTop5ByOrderByFechaDesc().stream()
                .map(pedido -> {
                    Mesa mesa = mesaRep.findById(pedido.getMesaId()).orElse(new Mesa());
                    User mesero = userRep.findById(pedido.getMeseroId()).orElse(new User());
                    pedido.setMesaNum(mesa.getNum());
                    pedido.setMeseroName(mesero.getName());
                    return pedido;
                })
                .collect(Collectors.toList());
    }

    private String validarDatos(Pedido pedido, List<String> platillosIds) {
        if (pedido.getMesaId() == null) return "Debe seleccionar una mesa";
        if (pedido.getMeseroId() == null) return "Debe seleccionar un mesero";
        if (platillosIds == null || platillosIds.isEmpty()) return "Debe seleccionar al menos un platillo";
        return "Error en los datos del formulario";
    }

    private Pedido crearNuevoPedido(Pedido pedido, Mesa mesa, User mesero,
                                    List<String> platillosIds, List<String> cantidades) {
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setMesaId(mesa.getId());
        nuevoPedido.setMesaNum(mesa.getNum());
        nuevoPedido.setMeseroId(mesero.getId());
        nuevoPedido.setMeseroName(mesero.getName());

        List<ItemPedido> items = new ArrayList<>();
        double total = 0.0;

        for (int i = 0; i < platillosIds.size(); i++) {
            String platilloId = platillosIds.get(i);
            String cantidadStr = cantidades.get(i);
            int cantidad = parseCantidad(cantidadStr);

            Platillo platillo = platilloRep.findById(platilloId)
                    .orElseThrow(() -> new IllegalArgumentException("Platillo no encontrado"));

            ItemPedido item = new ItemPedido();
            item.setName(platillo.getName());
            item.setCant(String.valueOf(cantidad));
            item.setPrice(platillo.getPrice());
            item.setProductId(platilloId);
            item.setUnidades(cantidad);

            items.add(item);
            total += platillo.getPrice() * cantidad;
        }

        nuevoPedido.setItems(items);
        nuevoPedido.setFecha(LocalDateTime.now());
        nuevoPedido.setTotal(total);
        nuevoPedido.setPagado(false);

        return nuevoPedido;
    }

    private int parseCantidad(String cantidadStr) {
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            return cantidad > 0 ? cantidad : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private void actualizarMesa(Mesa mesa, Pedido pedido, User mesero) {
        if (mesa.getPedidosId() == null) {
            mesa.setPedidosId(new ArrayList<>());
        }
        mesa.getPedidosId().add(pedido.getId());
        mesa.setOcupado(true);
        mesa.setMeseroId(mesero.getId());
        mesaRep.save(mesa);
    }

    private String cargarDatosModelo(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("mesas", mesaRep.findAll());
        model.addAttribute("platillos", platilloRep.findAll());

        List<User> meseros = userRep.findAll().stream()
                .filter(user -> user.getRol() != null &&
                        ("MESERO".equalsIgnoreCase(user.getRol()) ||
                                "ROLE_MESERO".equalsIgnoreCase(user.getRol())))
                .collect(Collectors.toList());
        model.addAttribute("meseros", meseros);

        model.addAttribute("pedidosRecientes", getPedidosConDetalles());
        return "pedido";
    }
}