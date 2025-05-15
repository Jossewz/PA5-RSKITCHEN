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

            // Cargar solo mesas no ocupadas
            List<Mesa> mesasDisponibles = mesaRep.findByOcupadoFalse();
            model.addAttribute("mesas", mesasDisponibles);

            // Cargar todos los platillos
            model.addAttribute("platillos", platilloRep.findAll());

            // Cargar solo meseros (con filtro mejorado)
            List<User> meseros = userRep.findAll().stream()
                    .filter(user -> user.getRol() != null &&
                            ("MESERO".equalsIgnoreCase(user.getRol()) ||
                                    "ROLE_MESERO".equalsIgnoreCase(user.getRol())))
                    .collect(Collectors.toList());
            model.addAttribute("meseros", meseros);

            // Cargar últimos pedidos
            model.addAttribute("pedidosRecientes", pedidoRep.findTop5ByOrderByFechaDesc());

            return "pedido";

        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el formulario de pedidos");
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/crear")
    public String crearPedido(@ModelAttribute("pedido") Pedido pedido,
                              BindingResult result,
                              @RequestParam(name = "platillosIds", required = false) List<String> platillosIds,
                              @RequestParam(name = "cantidades", required = false) List<String> cantidades,
                              Model model) {

        // Validación mejorada con mensajes específicos
        if (result.hasErrors()) {
            model.addAttribute("error", "Error en los datos del formulario");
            return cargarDatosModelo(model);
        }
        if (pedido.getMesaId() == null) {
            model.addAttribute("error", "Debe seleccionar una mesa");
            return cargarDatosModelo(model);
        }
        if (pedido.getMeseroId() == null) {
            model.addAttribute("error", "Debe seleccionar un mesero");
            return cargarDatosModelo(model);
        }
        if (platillosIds == null || platillosIds.isEmpty()) {
            model.addAttribute("error", "Debe seleccionar al menos un platillo");
            return cargarDatosModelo(model);
        }

        try {
            // Debug: Imprimir IDs para seguimiento
            System.out.println("[DEBUG] Intentando crear pedido para:");
            System.out.println(" - Mesa ID: " + pedido.getMesaId());
            System.out.println(" - Mesero ID: " + pedido.getMeseroId());
            System.out.println(" - Platillos IDs: " + platillosIds);

            // Verificar y obtener mesa
            Mesa mesa = mesaRep.findById(pedido.getMesaId())
                    .orElseThrow(() -> {
                        System.out.println("[ERROR] Mesa no encontrada con ID: " + pedido.getMesaId());
                        return new IllegalArgumentException("La mesa seleccionada no existe");
                    });

            // Verificar y obtener mesero con debug extendido
            Optional<User> meseroOpt = userRep.findById(pedido.getMeseroId());
            if (!meseroOpt.isPresent()) {
                System.out.println("[ERROR] Mesero no encontrado con ID: " + pedido.getMeseroId());
                System.out.println("[DEBUG] Meseros disponibles:");
                userRep.findAll().forEach(u -> {
                    if (u.getRol() != null &&
                            (u.getRol().equalsIgnoreCase("MESERO") ||
                                    u.getRol().equalsIgnoreCase("ROLE_MESERO"))) {
                        System.out.println(" - " + u.getId() + ": " + u.getName() + " (" + u.getRol() + ")");
                    }
                });
                throw new IllegalArgumentException("El mesero seleccionado no existe");
            }
            User mesero = meseroOpt.get();

            // Crear nuevo pedido
            Pedido nuevoPedido = new Pedido();
            nuevoPedido.setMesaId(pedido.getMesaId());
            nuevoPedido.setMeseroId(pedido.getMeseroId());

            // Procesar items del pedido
            List<ItemPedido> items = new ArrayList<>();
            double total = 0.0;

            for (int i = 0; i < platillosIds.size(); i++) {
                String platilloId = platillosIds.get(i);
                String cantidad = cantidades != null && i < cantidades.size() ? cantidades.get(i) : "1";

                Platillo platillo = platilloRep.findById(platilloId)
                        .orElseThrow(() -> {
                            System.out.println("[ERROR] Platillo no encontrado con ID: " + platilloId);
                            return new IllegalArgumentException("El platillo seleccionado no existe");
                        });

                ItemPedido item = new ItemPedido();
                item.setName(platillo.getName());
                item.setCant(cantidad);
                item.setPrice(platillo.getPrice());
                item.setProductId(platilloId);
                item.setUnidades(1);

                items.add(item);
                total += platillo.getPrice() * item.getUnidades();
            }

            nuevoPedido.setItems(items);
            nuevoPedido.setFecha(LocalDateTime.now());
            nuevoPedido.setTotal(total);
            nuevoPedido.setPagado(false);

            // Guardar pedido
            Pedido pedidoGuardado = pedidoRep.save(nuevoPedido);
            System.out.println("[DEBUG] Pedido creado con ID: " + pedidoGuardado.getId());

            // Actualizar mesa
            if (mesa.getPedidosId() == null) {
                mesa.setPedidosId(new ArrayList<>());
            }
            mesa.getPedidosId().add(pedidoGuardado.getId());
            mesa.setOcupado(true);
            mesa.setMeseroId(pedido.getMeseroId());
            mesaRep.save(mesa);
            System.out.println("[DEBUG] Mesa actualizada: " + mesa.getId());

            return "redirect:/pedidos?exito=Pedido+creado+correctamente";

        } catch (IllegalArgumentException e) {
            // Error de datos no encontrados
            model.addAttribute("error", e.getMessage());
            System.out.println("[ERROR] " + e.getMessage());
            return cargarDatosModelo(model);
        } catch (Exception e) {
            // Error inesperado
            String errorMsg = "Error inesperado al crear el pedido: " + e.getMessage();
            model.addAttribute("error", errorMsg);
            System.out.println("[ERROR] " + errorMsg);
            e.printStackTrace();
            return cargarDatosModelo(model);
        }
    }

    private String cargarDatosModelo(Model model) {
        try {
            // Cargar datos necesarios para mostrar el formulario nuevamente
            model.addAttribute("pedido", new Pedido());

            // Mostrar todas las mesas (no solo disponibles) para corrección de errores
            model.addAttribute("mesas", mesaRep.findAll());
            model.addAttribute("platillos", platilloRep.findAll());

            // Filtrar solo meseros
            List<User> meseros = userRep.findAll().stream()
                    .filter(user -> user.getRol() != null &&
                            ("MESERO".equalsIgnoreCase(user.getRol()) ||
                                    "ROLE_MESERO".equalsIgnoreCase(user.getRol())))
                    .collect(Collectors.toList());
            model.addAttribute("meseros", meseros);

            // Cargar pedidos recientes
            model.addAttribute("pedidosRecientes", pedidoRep.findTop5ByOrderByFechaDesc());

            return "pedido";
        } catch (Exception e) {
            System.out.println("[ERROR] Error al cargar datos del modelo: " + e.getMessage());
            model.addAttribute("error", "Error al cargar el formulario");
            return "error";
        }
    }
}