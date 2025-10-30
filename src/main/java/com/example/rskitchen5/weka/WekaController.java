package com.example.rskitchen5.weka;

import com.example.rskitchen5.weka.PedidoWekaService;
import com.example.rskitchen5.weka.WekaModelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import weka.core.Instances;

@RestController
public class WekaController {

    private final PedidoWekaService pedidoWekaService;
    private final WekaModelService wekaModelService;

    public WekaController(PedidoWekaService pedidoWekaService, WekaModelService wekaModelService) {
        this.pedidoWekaService = pedidoWekaService;
        this.wekaModelService = wekaModelService;
    }

    @GetMapping("/weka/prediccion")
    public String probarModelo() {
        Instances data = pedidoWekaService.obtenerInstancias();

        if (data.isEmpty()) {
            return "No hay datos disponibles para probar el modelo.";
        }

        try {
            double prediccion = wekaModelService.predecir(data.instance(0));
            return "Predicción del primer pedido: " + prediccion;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al realizar la predicción: " + e.getMessage();
        }
    }
}