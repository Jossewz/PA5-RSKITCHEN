package com.example.rskitchen5.weka;


import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.core.Instance;

import java.io.InputStream;
import java.io.ObjectInputStream;

@Service
public class WekaModelService {

    private Classifier model;

    public WekaModelService() {
        try {

            InputStream is = getClass().getResourceAsStream("/weka/modeloPedidos.model");

            ObjectInputStream ois = new ObjectInputStream(is);
            model = (Classifier) ois.readObject();
            ois.close();

            System.out.println("Modelo J48 cargado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar el modelo J48");
        }
    }

    public double predecir(Instance instance) throws Exception {
        return model.classifyInstance(instance);
    }
}