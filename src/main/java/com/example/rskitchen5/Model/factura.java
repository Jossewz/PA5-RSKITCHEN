package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "factura")
public class factura {

    @Id
    private String id;

    private String pedidoId;
    private String mesaId;
    private String meseroId;
    private LocalDateTime fecha;
    private double total;

    public factura() {
    }

    public factura(LocalDateTime fecha, String id, String mesaId, String pedidoId, String meseroId, double total) {
        this.fecha = fecha;
        this.id = id;
        this.mesaId = mesaId;
        this.pedidoId = pedidoId;
        this.meseroId = meseroId;
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

    public String getMeseroId() {
        return meseroId;
    }

    public void setMeseroId(String meseroId) {
        this.meseroId = meseroId;
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
