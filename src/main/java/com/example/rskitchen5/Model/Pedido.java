package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "pedido")
public class Pedido {

    @Id
    private String id;

    private String mesaId;
    private String meseroId;

    @Field("fecha")
    private LocalDateTime fecha; // Usar Date en lugar de LocalDateTime

    private List<ItemPedido> items;
    private double total;
    private boolean pagado;

    // Constructor y getters/setters...

    public Pedido() {

    }

    public Pedido(String mesaId, String id, String meseroId, LocalDateTime fecha, List<ItemPedido> items, double total, boolean pagado) {
        this.mesaId = mesaId;
        this.id = id;
        this.meseroId = meseroId;
        this.fecha = fecha;
        this.items = items;
        this.total = total;
        this.pagado = pagado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMesaId() {
        return mesaId;
    }

    public void setMesaId(String mesaId) {
        this.mesaId = mesaId;
    }

    public String getMeseroId() {
        return meseroId;
    }

    public void setMeseroId(String meseroId) {
        this.meseroId = meseroId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }
}

