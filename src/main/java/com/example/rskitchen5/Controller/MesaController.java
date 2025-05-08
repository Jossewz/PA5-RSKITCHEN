package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.Mesa;
import com.example.rskitchen5.Repository.MesaRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/mesa")
public class MesaController {

    private final MesaRep mesaRepository;

    @Autowired
    public MesaController(MesaRep mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @GetMapping("/mesa")
    public String mostrarVistaMesa() {
        return "mesa";
    }

    @GetMapping
    public List<Mesa> listarMesas() {
        return mesaRepository.findAll();
    }

    @PostMapping
    public Mesa crearMesa(@RequestBody Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> obtenerMesaPorId(@PathVariable String id) {
        return mesaRepository.findById(id)
                .map(mesa -> ResponseEntity.ok(mesa))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesa> actualizarMesa(@PathVariable String id, @RequestBody Mesa nuevaMesa) {
        return mesaRepository.findById(id)
                .map(mesa -> {
                    mesa.setNum(nuevaMesa.getNum());
                    mesa.setOcupado(nuevaMesa.isOcupado());
                    mesa.setMeseroId(nuevaMesa.getMeseroId());
                    mesa.setPedidosId(nuevaMesa.getPedidosId());
                    return ResponseEntity.ok(mesaRepository.save(mesa));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMesa(@PathVariable String id) {
        if (mesaRepository.existsById(id)) {
            mesaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}