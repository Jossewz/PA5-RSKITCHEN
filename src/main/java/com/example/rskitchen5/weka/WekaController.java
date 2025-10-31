package com.example.rskitchen5.weka;

import com.example.rskitchen5.weka.PedidoWekaService;
import com.example.rskitchen5.weka.WekaModelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import weka.core.Instances;

@Controller
public class WekaController {

    private final PedidoWekaService pedidoWekaService;
    private final WekaModelService wekaModelService;

    public WekaController(PedidoWekaService pedidoWekaService, WekaModelService wekaModelService) {
        this.pedidoWekaService = pedidoWekaService;
        this.wekaModelService = wekaModelService;
    }

    @GetMapping("/datos")
    public String mostrarVista() {
        return "datos";
    }

    @GetMapping("/weka")
    @ResponseBody
    public String probarModelo() {
        Instances data = pedidoWekaService.obtenerInstancias();

        if (data.isEmpty()) {
            return "No hay datos disponibles para probar el modelo.";
        }

        try {
            if (data.classIndex() < 0) {
                data.setClassIndex(data.numAttributes() - 1);
            }

            double prediccion = wekaModelService.predecir(data.instance(0));
            return "Predicción del primer pedido: " + prediccion;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al realizar la predicción: " + e.getMessage();
        }
    }
}
