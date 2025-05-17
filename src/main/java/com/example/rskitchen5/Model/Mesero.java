package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Mesero {

    @Id
    private String id_mesero;

    private String name;
    private String mail;
    private String password;
    private String rol;

    public String getId_mesero() {
        return id_mesero;
    }

    public void setId_mesero(String id_mesero) {
        this.id_mesero = id_mesero;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRole() {
        return rol;
    }

    public void setRole(String rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
