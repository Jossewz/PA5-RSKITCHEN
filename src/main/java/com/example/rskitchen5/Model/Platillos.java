package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "platillos")
public class Platillos {

    @Id
    private Long id;

    private String name;

}