package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.factura;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacturaRep extends MongoRepository<factura, String> {
    @Override
    Optional<factura> findById(String s);
}

