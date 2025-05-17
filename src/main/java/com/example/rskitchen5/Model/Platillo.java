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
    private List<String> ingredients;
    private String cant;
    private String description;

    public Platillo() {
    }

    public Platillo(String cant, String description, String id, List<String> ingredients, String name, double price) {
        this.cant = cant;
        this.description = description;
        this.id = id;
        this.ingredients = ingredients;
        this.name = name;
        this.price = price;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
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
