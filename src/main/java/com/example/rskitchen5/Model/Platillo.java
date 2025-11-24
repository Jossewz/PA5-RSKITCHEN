package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "platillos")
public class Platillo {

    @Id
    private String id;

    private String name;
    private double price;
    private String cant;

    public Platillo() {
    }

    public Platillo(String cant, String description, String id, List<String> ingredients, String name, double price) {
        this.cant = cant;
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
