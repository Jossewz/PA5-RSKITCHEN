package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "facturas")
public class Factura {
    @Id
    private String id;
    private String pedidoId;
    private String mesaNum;
    private String meseroName;
    private LocalDateTime fecha;
    private List<ItemPedido> items;
    private double total;

    public Factura() {
    }

    public Factura(LocalDateTime fecha, String id, List<ItemPedido> items, String mesaNum, String meseroName, String pedidoId, double total) {
        this.fecha = fecha;
        this.id = id;
        this.items = items;
        this.mesaNum = mesaNum;
        this.meseroName = meseroName;
        this.pedidoId = pedidoId;
        this.total = total;

    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }

    public String getMesaNum() {
        return mesaNum;
    }

    public void setMesaNum(String mesaNum) {
        this.mesaNum = mesaNum;
    }

    public String getMeseroName() {
        return meseroName;
    }

    public void setMeseroName(String meseroName) {
        this.meseroName = meseroName;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
