package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "foro")
public class Foro {

    @Id
    private String id;
    private String fnombre;
    private String fcomentario;
    private LocalDateTime fecha;

    public Foro(String fnombre, String fcomentario, LocalDateTime fecha) {
        this.fnombre = fnombre;
        this.fcomentario = fcomentario;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFnombre() {
        return fnombre;
    }

    public void setFnombre(String fnombre) {
        this.fnombre = fnombre;
    }

    public String getFcomentario() {
        return fcomentario;
    }

    public void setFcomentario(String fcomentario) {
        this.fcomentario = fcomentario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

