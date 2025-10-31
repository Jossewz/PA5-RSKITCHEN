package com.example.rskitchen5.weka;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.SerializationHelper;

public class EntrenarModelo {
    public static void main(String[] args) throws Exception {
        // Carga el dataset
        DataSource source = new DataSource("facturas.arff");
        Instances data = source.getDataSet();
        data.setClassIndex(data.numAttributes() - 1);

        // Entrena el modelo
        J48 tree = new J48();
        tree.buildClassifier(data);

        // Guarda el modelo
        SerializationHelper.write("j48_factura.model", tree);

        System.out.println("Modelo guardado como j48_factura.model");
    }
}