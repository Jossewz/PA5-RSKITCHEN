package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.*;
import com.example.rskitchen5.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private FacturaRep facturaRep;

    @GetMapping
    public String mostrarFormularioPedido(@RequestParam(name = "id", required = false) String id, Model model) {
        try {
            Pedido pedido;
            boolean modoEdicion = false;

            if (id != null && !id.isEmpty()) {
                pedido = pedidoRep.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
                modoEdicion = true;
            } else {
                pedido = new Pedido();
            }

            model.addAttribute("pedido", pedido);
            model.addAttribute("modoEdicion", modoEdicion);
            model.addAttribute("mesas", mesaRep.findByOcupadoFalse());
            model.addAttribute("platillos", platilloRep.findAll());

            List<User> meseros = userRep.findAll().stream()
                    .filter(user -> user.getRol() != null &&
                            ("MESERO".equalsIgnoreCase(user.getRol()) || "ROLE_MESERO".equalsIgnoreCase(user.getRol())))
                    .collect(Collectors.toList());
            model.addAttribute("meseros", meseros);

            model.addAttribute("pedidosRecientes", getPedidosConDetalles());
            return "pedido";

        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el formulario de pedidos: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") String id, Model model) {
        try {
            Pedido pedido = pedidoRep.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

            User mesero = userRep.findById(pedido.getMeseroId()).orElse(new User());
            pedido.setMeseroName(mesero.getName());

            model.addAttribute("pedido", pedido);
            model.addAttribute("mesas", mesaRep.findByOcupadoFalse());
            model.addAttribute("platillosDisponibles", platilloRep.findAll());

            List<User> meseros = userRep.findAll().stream()
                    .filter(user -> user.getRol() != null &&
                            ("MESERO".equalsIgnoreCase(user.getRol()) || "ROLE_MESERO".equalsIgnoreCase(user.getRol())))
                    .collect(Collectors.toList());
            model.addAttribute("meseros", meseros);

            return "pedido-editar";

        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el formulario de edición: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/editar")
    public String editarPedido(
            @RequestParam("id") String pedidoId,
            @RequestParam("mesaId") String mesaId,
            @RequestParam(value = "itemsExistentes[].platillo.id", required = false) List<String> platillosExistentesIds,
            @RequestParam(value = "itemsExistentes[].cantidad", required = false) List<Integer> cantidadesExistentes,
            @RequestParam(value = "nuevosItems[].platillo.id", required = false) List<String> platillosNuevosIds,
            @RequestParam(value = "nuevosItems[].cantidad", required = false) List<Integer> cantidadesNuevas,
            Model model) {

        try {
            Pedido pedido = pedidoRep.findById(pedidoId)
                    .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

            Mesa mesa = mesaRep.findById(mesaId)
                    .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
            pedido.setMesaId(mesaId);
            pedido.setMesaNum(mesa.getNum());
            pedido.setFecha(LocalDateTime.now());

            List<ItemPedido> itemsActualizados = new ArrayList<>();

            if (platillosExistentesIds != null && cantidadesExistentes != null &&
                    platillosExistentesIds.size() == cantidadesExistentes.size()) {

                for (int i = 0; i < platillosExistentesIds.size(); i++) {
                    String platilloId = platillosExistentesIds.get(i);
                    Integer cantidad = cantidadesExistentes.get(i);

                    if (platilloId != null && cantidad != null && cantidad > 0) {
                        Platillo platillo = platilloRep.findById(platilloId)
                                .orElseThrow(() -> new IllegalArgumentException("Platillo no encontrado"));

                        ItemPedido item = new ItemPedido();
                        item.setProductId(platilloId);
                        item.setName(platillo.getName());
                        item.setPrice(platillo.getPrice());
                        item.setUnidades(cantidad);
                        item.setCant(String.valueOf(cantidad));

                        itemsActualizados.add(item);
                    }
                }
            }

            if (platillosNuevosIds != null && cantidadesNuevas != null) {
                for (int i = 0; i < platillosNuevosIds.size(); i++) {
                    String platilloId = platillosNuevosIds.get(i);
                    if (platilloId != null && !platilloId.isEmpty()) {
                        Integer cantidad = cantidadesNuevas.get(i);

                        if (cantidad != null && cantidad > 0) {
                            Platillo platillo = platilloRep.findById(platilloId)
                                    .orElseThrow(() -> new IllegalArgumentException("Platillo no encontrado"));

                            Optional<ItemPedido> itemExistente = itemsActualizados.stream()
                                    .filter(item -> item.getProductId().equals(platilloId))
                                    .findFirst();

                            if (itemExistente.isPresent()) {
                                ItemPedido item = itemExistente.get();
                                item.setUnidades(item.getUnidades() + cantidad);
                                item.setCant(String.valueOf(item.getUnidades()));
                            } else {
                                ItemPedido nuevoItem = new ItemPedido();
                                nuevoItem.setProductId(platilloId);
                                nuevoItem.setName(platillo.getName());
                                nuevoItem.setPrice(platillo.getPrice());
                                nuevoItem.setUnidades(cantidad);
                                nuevoItem.setCant(String.valueOf(cantidad));
                                itemsActualizados.add(nuevoItem);
                            }
                        }
                    }
                }
            }

            pedido.setItems(itemsActualizados);
            pedido.setTotal(calcularTotal(itemsActualizados));
            pedidoRep.save(pedido);

            actualizarEstadoMesa(mesa, pedidoId);

            return "redirect:/pedidos?exito=Pedido+editado+correctamente";

        } catch (Exception e) {
            model.addAttribute("error", "Error al editar el pedido: " + e.getMessage());
            Pedido pedido = pedidoRep.findById(pedidoId).orElse(new Pedido());
            recargarDatosModelo(model, pedido);
            return "pedido-editar";
        }
    }
    @PostMapping
    public String guardarPedido(@ModelAttribute("pedido") Pedido pedido,
                                BindingResult result,
                                @RequestParam(name = "platillosIds", required = false) List<String> platillosIds,
                                @RequestParam(name = "cantidades", required = false) List<String> cantidades,
                                Model model) {

        boolean esEdicion = pedido.getId() != null && !pedido.getId().isEmpty();

        if (result.hasErrors() || pedido.getMesaId() == null || pedido.getMeseroId() == null ||
                platillosIds == null || platillosIds.isEmpty()) {
            model.addAttribute("error", validarDatos(pedido, platillosIds));
            model.addAttribute("modoEdicion", esEdicion);
            return cargarDatosModelo(model, pedido);
        }

        try {
            Mesa mesa = mesaRep.findById(pedido.getMesaId())
                    .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
            User mesero = userRep.findById(pedido.getMeseroId())
                    .orElseThrow(() -> new IllegalArgumentException("Mesero no encontrado"));

            if (esEdicion) {
                return actualizarPedidoExistente(pedido, mesa, mesero, platillosIds, cantidades);
            } else {
                return crearNuevoPedido(pedido, mesa, mesero, platillosIds, cantidades);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el pedido: " + e.getMessage());
            model.addAttribute("modoEdicion", esEdicion);
            return cargarDatosModelo(model, pedido);
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        try {
            Pedido pedido = pedidoRep.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

            Mesa mesa = mesaRep.findById(pedido.getMesaId()).orElse(null);
            if (mesa != null && mesa.getPedidosId() != null) {
                mesa.getPedidosId().remove(pedido.getId());
                if (mesa.getPedidosId().isEmpty()) {
                    mesa.setOcupado(false);
                    mesa.setMeseroId(null);
                }
                mesaRep.save(mesa);
            }

            pedidoRep.delete(pedido);
            redirectAttributes.addFlashAttribute("exito", "Pedido eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el pedido: " + e.getMessage());
        }
        return "redirect:/pedidos";
    }

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

    private int parseCantidad(String cantidadStr) {
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            return cantidad > 0 ? cantidad : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private void actualizarEstadoMesa(Mesa mesa, String pedidoId) {
        if (mesa.getPedidosId() == null) {
            mesa.setPedidosId(new ArrayList<>());
        }
        if (!mesa.getPedidosId().contains(pedidoId)) {
            mesa.getPedidosId().add(pedidoId);
        }
        mesa.setOcupado(true);
        mesaRep.save(mesa);
    }

    private double calcularTotal(List<ItemPedido> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getUnidades())
                .sum();
    }

    private void recargarDatosModelo(Model model, Pedido pedido) {
        model.addAttribute("pedido", pedido);
        model.addAttribute("mesas", mesaRep.findByOcupadoFalse());
        model.addAttribute("platillosDisponibles", platilloRep.findAll());

        List<User> meseros = userRep.findAll().stream()
                .filter(user -> user.getRol() != null &&
                        ("MESERO".equalsIgnoreCase(user.getRol()) ||
                                "ROLE_MESERO".equalsIgnoreCase(user.getRol())))
                .collect(Collectors.toList());
        model.addAttribute("meseros", meseros);
    }

    private String actualizarPedidoExistente(Pedido pedido, Mesa mesa, User mesero,
                                             List<String> platillosIds, List<String> cantidades) {
        Pedido pedidoExistente = pedidoRep.findById(pedido.getId())
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

        pedidoExistente.setMesaId(mesa.getId());
        pedidoExistente.setMesaNum(mesa.getNum());
        pedidoExistente.setMeseroId(mesero.getId());
        pedidoExistente.setMeseroName(mesero.getName());

        List<ItemPedido> items = new ArrayList<>();
        for (int i = 0; i < platillosIds.size(); i++) {
            String platilloId = platillosIds.get(i);
            int cantidad = parseCantidad(cantidades.get(i));
            Platillo platillo = platilloRep.findById(platilloId)
                    .orElseThrow(() -> new IllegalArgumentException("Platillo no encontrado"));

            ItemPedido item = new ItemPedido();
            item.setName(platillo.getName());
            item.setCant(String.valueOf(cantidad));
            item.setPrice(platillo.getPrice());
            item.setProductId(platilloId);
            item.setUnidades(cantidad);
            items.add(item);
        }

        pedidoExistente.setItems(items);
        pedidoExistente.setTotal(calcularTotal(items));
        pedidoRep.save(pedidoExistente);

        return "redirect:/pedidos?exito=Pedido+editado+correctamente";
    }

    private String crearNuevoPedido(Pedido pedido, Mesa mesa, User mesero,
                                    List<String> platillosIds, List<String> cantidades) {
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setMesaId(mesa.getId());
        nuevoPedido.setMesaNum(mesa.getNum());
        nuevoPedido.setMeseroId(mesero.getId());
        nuevoPedido.setMeseroName(mesero.getName());
        nuevoPedido.setFecha(LocalDateTime.now());

        List<ItemPedido> items = new ArrayList<>();
        double total = 0.0;

        for (int i = 0; i < platillosIds.size(); i++) {
            String platilloId = platillosIds.get(i);
            int cantidad = parseCantidad(cantidades.get(i));
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
        nuevoPedido.setTotal(total);
        Pedido pedidoGuardado = pedidoRep.save(nuevoPedido);

        actualizarMesa(mesa, pedidoGuardado, mesero);

        return "redirect:/pedidos?exito=Pedido+creado+correctamente";
    }

    private void actualizarMesa(Mesa mesa, Pedido pedido, User mesero) {
        if (mesa.getPedidosId() == null) {
            mesa.setPedidosId(new ArrayList<>());
        }
        if (!mesa.getPedidosId().contains(pedido.getId())) {
            mesa.getPedidosId().add(pedido.getId());
        }
        mesa.setMeseroId(mesero.getId());
        mesa.setOcupado(true);
        mesaRep.save(mesa);
    }

    private String cargarDatosModelo(Model model, Pedido pedido) {
        model.addAttribute("pedido", pedido);
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
    }
}
