package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.factura;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacturaRep extends MongoRepository<factura, String> {
}

