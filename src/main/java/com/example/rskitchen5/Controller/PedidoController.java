package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.Pedido;
import com.example.rskitchen5.Repository.PedidoRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRep pedidoRep;

    @GetMapping
    public List<Pedido>getAllPedidos(){
        return pedidoRep.findAll();
    }

    @PostMapping
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        return pedidoRep.save(pedido);
    }

    @PutMapping("/{id}")
    public Pedido updatePedido(@PathVariable String id, @RequestBody Pedido pedido) {
        Pedido existingPedido = pedidoRep.findById(id).orElseThrow();
        existingPedido.setMesaId(pedido.getMesaId());
        existingPedido.setFecha(pedido.getFecha());
        existingPedido.setMeseroId(pedido.getMeseroId());
        existingPedido.setItems(pedido.getItems());
        existingPedido.setTotal(pedido.getTotal());
        existingPedido.setPagado(pedido.isPagado());
        return pedidoRep.save(existingPedido);
    }

    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable String id) {
        pedidoRep.deleteById(id);
    }
}
