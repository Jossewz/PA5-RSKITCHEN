package com.example.rskitchen5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "pedido")
public class Pedidos {

    @Id
    private Long id;

    private String mesaId; //id de Mesa que hizo el pedido
    private String meseroId; //id de mesero que atendió
    private LocalDateTime fecha;
    private List<ItemPedido> items;
    private double total;
    private boolean pagado;


}
