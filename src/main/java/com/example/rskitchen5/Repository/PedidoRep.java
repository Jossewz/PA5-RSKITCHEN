package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRep extends MongoRepository<Pedido, String> { }


