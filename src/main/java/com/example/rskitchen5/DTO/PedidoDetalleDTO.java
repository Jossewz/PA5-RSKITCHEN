package com.example.rskitchen5.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoDetalleDTO {

    private String id;
    private LocalDateTime fecha;
    private double total;
    private boolean pagado;
    private String nombreMesero;
    private List<ItemPedidoDetalleDTO> items;

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

    public List<ItemPedidoDetalleDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemPedidoDetalleDTO> items) {
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

    public String getNombreMesero() {
        return nombreMesero;
    }

    public void setNombreMesero(String nombreMesero) {
        this.nombreMesero = nombreMesero;
    }
}
