package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.Mesero;
import com.example.rskitchen5.Model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeseroRep extends MongoRepository<Mesero, String> {
    @Override
    Optional<Mesero> findById (String id);
}
