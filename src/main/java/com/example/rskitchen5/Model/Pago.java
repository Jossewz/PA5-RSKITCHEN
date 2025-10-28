package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "pagos")
public class Pago {

    @Id
    private String id;
    private String facturaId;
    private String metodo; //metodo de pago
    private double monto; // a pagar
    private LocalDateTime fecha;
    private boolean imprimirCopia;

    public Pago() {
    }

    public Pago(String facturaId, LocalDateTime fecha, String id, boolean imprimirCopia, String metodo, double monto) {
        this.facturaId = facturaId;
        this.fecha = fecha;
        this.id = id;
        this.imprimirCopia = imprimirCopia;
        this.metodo = metodo;
        this.monto = monto;
    }

    public String getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(String facturaId) {
        this.facturaId = facturaId;
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

    public boolean isImprimirCopia() {
        return imprimirCopia;
    }

    public void setImprimirCopia(boolean imprimirCopia) {
        this.imprimirCopia = imprimirCopia;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
