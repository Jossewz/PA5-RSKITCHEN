package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.Mesa;
import com.example.rskitchen5.Repository.MesaRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mesa")
public class MesaController {

    private final MesaRep mesaRepository;

    @Autowired
    public MesaController(MesaRep mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @GetMapping // Para servir el HTML de la página (si es necesario)
    public String mostrarVistaMesa() {
        return "mesa"; // Retorna el nombre de la vista (archivo HTML)
    }

    @GetMapping("/listar") // Nueva ruta para obtener los datos de las mesas en JSON
    public List<Mesa> listarMesas() {
        return mesaRepository.findAll();
    }

    // El resto de tu controlador (POST, GET por ID, PUT, DELETE) se queda igual
    @PostMapping
    public ResponseEntity<?> crearMesa(@RequestBody Mesa mesa) {
        try {
            Mesa nuevaMesa = mesaRepository.save(mesa);
            return ResponseEntity.ok(nuevaMesa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error interno al crear la mesa: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> obtenerMesaPorId(@PathVariable String id) {
        return mesaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesa> actualizarMesa(@PathVariable String id, @RequestBody Mesa mesaActualizada) {
        return mesaRepository.findById(id)
                .map(mesaExistente -> {
                    mesaExistente.setNum(mesaActualizada.getNum());
                    mesaExistente.setOcupado(mesaActualizada.isOcupado());
                    mesaExistente.setMeseroId(mesaActualizada.getMeseroId());
                    Mesa guardada = mesaRepository.save(mesaExistente);
                    return ResponseEntity.ok(guardada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMesa(@PathVariable String id) {
        if (mesaRepository.existsById(id)) {
            mesaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}