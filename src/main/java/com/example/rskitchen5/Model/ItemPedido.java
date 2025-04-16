package com.example.rskitchen5.Model;

public class ItemPedido {
    private Long productId;       // ID del producto original
    private String name;         // Nombre del producto en el momento del pedido
    private double price;         // Precio unitario en ese momento
    private String cant;           // Cantidad solicitada (ej: "2", "1.5", "una porción")
    private int unidades;

    public ItemPedido() {
    }

    public ItemPedido(String name, String cant, double price, int unidades, Long productId) {
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
