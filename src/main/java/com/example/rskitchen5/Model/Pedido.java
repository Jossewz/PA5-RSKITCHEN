package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "pedido")
public class Pedido {

    @Id
    private String id;

    @Transient
    private String mesaNum;

    @Transient
    private String meseroName;

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

    public Pedido(LocalDateTime fecha, String id, List<ItemPedido> items, String mesaNum, String mesaId, String meseroId, boolean pagado, double total, String meseroName) {
        this.fecha = fecha;
        this.id = id;
        this.items = items;
        this.mesaNum = mesaNum;
        this.mesaId = mesaId;
        this.meseroId = meseroId;
        this.pagado = pagado;
        this.total = total;
        this.meseroName = meseroName;
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

    public String getMesaId() {
        return mesaId;
    }

    public void setMesaId(String mesaId) {
        this.mesaId = mesaId;
    }

    public String getMesaNum() {
        return mesaNum;
    }

    public void setMesaNum(String mesaNum) {
        this.mesaNum = mesaNum;
    }

    public String getMeseroId() {
        return meseroId;
    }

    public void setMeseroId(String meseroId) {
        this.meseroId = meseroId;
    }

    public String getMeseroName() {
        return meseroName;
    }

    public void setMeseroName(String meseroName) {
        this.meseroName = meseroName;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}