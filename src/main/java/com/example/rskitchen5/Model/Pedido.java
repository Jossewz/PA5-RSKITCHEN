package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "pedido")
public class Pedido {

    @Id
    private String id;

    private String mesaId;
    private String meseroId;
    private LocalDateTime fecha;
    private List<ItemPedido> items;
    private double total;
    private boolean pagado;

    public Pedido() {
    }

    public Pedido(LocalDateTime fecha, String id, List<ItemPedido> items, String mesaId, String meseroId, boolean pagado, double total) {
        this.fecha = fecha;
        this.id = id;
        this.items = items;
        this.mesaId = mesaId;
        this.meseroId = meseroId;
        this.pagado = pagado;
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

    public String getMesaId() {
        return mesaId;
    }

    public void setMesaId(String mesaId) {
        this.mesaId = mesaId;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }

    public String getMeseroId() {
        return meseroId;
    }

    public void setMeseroId(String meseroId) {
        this.meseroId = meseroId;
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
