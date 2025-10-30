package com.example.rskitchen5.weka;

import com.example.rskitchen5.Model.Pedido;
import com.example.rskitchen5.Repository.PedidoRep;
import org.springframework.stereotype.Service;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PedidoWekaService {

    private final PedidoRep pedidoRepository;

    public PedidoWekaService(PedidoRep pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Instances obtenerInstancias() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        ArrayList<Attribute> atributos = new ArrayList<>();
        atributos.add(new Attribute("total"));
        atributos.add(new Attribute("fecha"));

        Instances data = new Instances("Pedidos", atributos, pedidos.size());

        for (Pedido p : pedidos) {
            double[] valores = new double[data.numAttributes()];

            valores[0] = p.getTotal();
            valores[1] = (p.getFecha() != null)
                    ? Date.from(p.getFecha().atZone(ZoneId.systemDefault()).toInstant()).getTime()
                    : 0;

            data.add(new DenseInstance(1.0, valores));
        }

        return data;
    }
}
