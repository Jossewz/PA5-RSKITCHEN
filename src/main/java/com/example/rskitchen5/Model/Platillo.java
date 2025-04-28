package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "platillos")
public class Platillo {

    @Id
    private Long id;

    private String name;
    private double price;
    private List<String> ingredietns;
    private String cant;

    public Platillo() {
    }

    public Platillo(String cant, Long id, List<String> ingredietns, String name, double price) {
        this.cant = cant;
        this.id = id;
        this.ingredietns = ingredietns;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getIngredietns() {
        return ingredietns;
    }

    public void setIngredietns(List<String> ingredietns) {
        this.ingredietns = ingredietns;
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