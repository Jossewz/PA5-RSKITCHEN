package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "mesa")
public class Mesa {

    @Id
    private String id;

    private int num;
    private boolean ocupado;
    private String meseroId;
    private List<String> pedidosId;

    public Mesa() {
    }

    public Mesa(String id, String meseroId, List<String> pedidosId, boolean ocupado, int num) {
        this.id = id;
        this.meseroId = meseroId;
        this.pedidosId = pedidosId;
        this.ocupado = ocupado;
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeseroId() {
        return meseroId;
    }

    public void setMeseroId(String meseroId) {
        this.meseroId = meseroId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public List<String> getPedidosId() {
        return pedidosId;
    }

    public void setPedidosId(List<String> pedidosId) {
        this.pedidosId = pedidosId;
    }
}
