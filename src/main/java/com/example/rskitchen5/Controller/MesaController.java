package com.example.rskitchen5.Controller;

import com.example.rskitchen5.DTO.ItemPedidoDetalleDTO;
import com.example.rskitchen5.DTO.PedidoDetalleDTO;
import com.example.rskitchen5.Model.ItemPedido;
import com.example.rskitchen5.Model.Mesa;
import com.example.rskitchen5.Model.Pedido;
import com.example.rskitchen5.Repository.MesaRep;
import com.example.rskitchen5.Repository.PedidoRep;
import com.example.rskitchen5.Repository.PlatilloRep;
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
    private final PlatilloRep platilloRep;

    @Autowired
    public MesaController(MesaRep mesaRepository, PedidoRep pedidoRep,
                          UserRep userRep, PlatilloRep platilloRep) {
        this.mesaRepository = mesaRepository;
        this.pedidoRep = pedidoRep;
        this.userRep = userRep;
        this.platilloRep = platilloRep;
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

            // Obtener detalles completos de los pedidos
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

            // Obtener nombre del mesero
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

        // Obtener nombre del mesero
        if (pedido.getMeseroId() != null) {
            userRep.findById(pedido.getMeseroId())
                    .ifPresent(mesero -> dto.setNombreMesero(mesero.getName()));
        }

        // Convertir items con nombres completos de platillos
        List<ItemPedidoDetalleDTO> itemsDTO = new ArrayList<>();
        for (ItemPedido item : pedido.getItems()) {
            ItemPedidoDetalleDTO itemDTO = new ItemPedidoDetalleDTO();
            itemDTO.setCantidad(item.getCant());
            itemDTO.setPrecio(item.getPrice());

            // Obtener nombre del platillo
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
}