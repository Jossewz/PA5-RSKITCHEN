package com.example.rskitchen5.Model;

public class ItemPedido {
    private String  productId;
    private String name;
    private double price;
    private String cant;
    private int unidades;

    public ItemPedido() {
    }

    public ItemPedido(String name, String cant, double price, int unidades, String productId) {
        this.name = name;
        this.cant = cant;
        this.price = price;
        this.unidades = unidades;
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public String  getProductId() {
        return productId;
    }

    public void setProductId(String  productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNombre(String name) {
    }

    public void setPrecio(double price) {
    }

    public void setCantidad(int i) {

    }
}
