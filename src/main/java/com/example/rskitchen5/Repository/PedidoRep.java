package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRep extends MongoRepository<Pedido, String> {
    @Override
    Optional<Pedido> findById(String id);
    List<Pedido> findTop5ByOrderByFechaDesc();
}


