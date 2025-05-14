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
    private List<String> ingredients;  // Cambié el nombre del campo a ingredients
    private String cant;

    public Platillo() {
    }

    public Platillo(String cant, Long id, List<String> ingredients, String name, double price) {
        this.cant = cant;
        this.id = id;
        this.ingredients = ingredients;  // Cambié el nombre aquí también
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

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {  // Cambié el nombre del setter
        this.ingredients = ingredients;
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
