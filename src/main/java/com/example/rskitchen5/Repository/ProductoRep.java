package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRep extends MongoRepository<Producto, String> {
    @Override
    Optional<Producto> findById (String id);
}