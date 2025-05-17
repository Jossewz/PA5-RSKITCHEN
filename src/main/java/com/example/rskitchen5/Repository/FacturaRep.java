package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.Factura;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacturaRep extends MongoRepository<Factura, String> {
    @Override
    Optional<Factura> findById(String id);

}

