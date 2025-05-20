package com.example.rskitchen5.Controller;

import com.example.rskitchen5.DTO.ItemPedidoDetalleDTO;
import com.example.rskitchen5.DTO.PedidoDetalleDTO;
import com.example.rskitchen5.Model.Factura;
import com.example.rskitchen5.Model.ItemPedido;
import com.example.rskitchen5.Model.Mesa;
import com.example.rskitchen5.Model.Pedido;
import com.example.rskitchen5.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/mesa")
public class MesaController {

    private final MesaRep mesaRepository;
    private final PedidoRep pedidoRep;
    private final UserRep userRep;
    private final PlatilloRep platilloRep;
    private FacturaRep facturaRep;

    @Autowired
    public MesaController(MesaRep mesaRepository, PedidoRep pedidoRep,
                          UserRep userRep, PlatilloRep platilloRep, FacturaRep facturaRep) {
        this.mesaRepository = mesaRepository;
        this.pedidoRep = pedidoRep;
        this.userRep = userRep;
        this.platilloRep = platilloRep;
        this.facturaRep = facturaRep;
    }

    @GetMapping("")
    public String mostrarVistaMesa(Model model) {
        List<Mesa> mesas = mesaRepository.findAll();
        Map<String, String> nombresMeseros = new HashMap<>();
        Map<String, List<PedidoDetalleDTO>> pedidosPorMesa = new HashMap<>();

        for (Mesa mesa : mesas) {
            if (mesa.getMeseroId() != null) {
                userRep.findById(mesa.getMeseroId())
                        .ifPresent(mesero -> nombresMeseros.put(mesa.getId(), mesero.getName()));
            }

            List<PedidoDetalleDTO> pedidosDTO = new ArrayList<>();
            if (mesa.getPedidosId() != null) {
                for (String pedidoId : mesa.getPedidosId()) {
                    pedidoRep.findById(pedidoId).ifPresent(pedido -> {
                        PedidoDetalleDTO dto = convertirPedidoADTO(pedido);
                        pedidosDTO.add(dto);
                    });
                }
            }
            pedidosPorMesa.put(mesa.getId(), pedidosDTO);
        }

        model.addAttribute("listar", mesas);
        model.addAttribute("nuevaMesa", new Mesa());
        model.addAttribute("nombresMeseros", nombresMeseros);
        model.addAttribute("pedidosPorMesa", pedidosPorMesa);
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

            List<PedidoDetalleDTO> pedidosDTO = new ArrayList<>();
            if (mesa.getPedidosId() != null) {
                for (String pedidoId : mesa.getPedidosId()) {
                    pedidoRep.findById(pedidoId).ifPresent(pedido -> {
                        PedidoDetalleDTO dto = convertirPedidoADTO(pedido);
                        pedidosDTO.add(dto);
                    });
                }
            }
            model.addAttribute("pedidos", pedidosDTO);

            if (mesa.getMeseroId() != null) {
                userRep.findById(mesa.getMeseroId())
                        .ifPresent(mesero -> model.addAttribute("nombreMesero", mesero.getName()));
            }
        });

        return "mesa";
    }

    private PedidoDetalleDTO convertirPedidoADTO(Pedido pedido) {
        PedidoDetalleDTO dto = new PedidoDetalleDTO();
        dto.setId(pedido.getId());
        dto.setFecha(pedido.getFecha());
        dto.setTotal(pedido.getTotal());
        dto.setPagado(pedido.isPagado());

        if (pedido.getMeseroId() != null) {
            userRep.findById(pedido.getMeseroId())
                    .ifPresent(mesero -> dto.setNombreMesero(mesero.getName()));
        }

        List<ItemPedidoDetalleDTO> itemsDTO = new ArrayList<>();
        for (ItemPedido item : pedido.getItems()) {
            ItemPedidoDetalleDTO itemDTO = new ItemPedidoDetalleDTO();
            itemDTO.setCantidad(item.getCant());
            itemDTO.setPrecio(item.getPrice());

            platilloRep.findById(item.getProductId())
                    .ifPresent(platillo -> {
                        itemDTO.setNombrePlatillo(platillo.getName());
                        itemDTO.setDescripcionPlatillo(platillo.getDescription());
                    });

            itemsDTO.add(itemDTO);
        }
        dto.setItems(itemsDTO);

        return dto;
    }

    @PostMapping("/{id}/generar-factura")
    public String generarFactura(@PathVariable String id, Model model) {
        Optional<Mesa> optionalMesa = mesaRepository.findById(id);

        if (optionalMesa.isEmpty()) {
            model.addAttribute("mensajeError", "Mesa no encontrada");
            return "redirect:/mesa";
        }

        Mesa mesa = optionalMesa.get();

        if (mesa.getPedidosId() == null || mesa.getPedidosId().isEmpty()) {
            model.addAttribute("mensajeError", "La mesa no tiene pedidos para facturar");
            return "redirect:/mesa";
        }

        String pedidoId = mesa.getPedidosId().get(0);
        Optional<Pedido> optionalPedido = pedidoRep.findById(pedidoId);

        if (optionalPedido.isEmpty()) {
            model.addAttribute("mensajeError", "Pedido no encontrado");
            return "redirect:/mesa";
        }

        Pedido pedido = optionalPedido.get();

        Factura factura = new Factura();
        factura.setPedidoId(pedido.getId());
        factura.setMesaNum(String.valueOf(mesa.getNum()));

        if (mesa.getMeseroId() != null) {
            userRep.findById(mesa.getMeseroId())
                    .ifPresent(mesero -> factura.setMeseroName(mesero.getName()));
        }

        factura.setFecha(LocalDateTime.now());
        factura.setItems(pedido.getItems());
        factura.setTotal(pedido.getTotal());

        facturaRep.save(factura);

        mesa.setOcupado(false);
        mesa.setMeseroId(null);
        mesa.setPedidosId(new ArrayList<>());
        mesaRepository.save(mesa);

        model.addAttribute("mensajeExito", "Factura generada y mesa liberada con éxito");

        return "redirect:/mesa";
    }
}