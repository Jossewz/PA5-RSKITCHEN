package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productos")
public class Productos {

    @Id
    private Long id;

    private String name;
    private String cant;
    private String price;

    public Productos() {
    }

    public Productos(String name, Long id, String cant, String price) {
        this.name = name;
        this.id = id;
        this.cant = cant;
        this.price = price;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}