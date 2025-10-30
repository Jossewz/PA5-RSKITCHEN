package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.Pago;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PagoRep extends MongoRepository <Pago, String> {
}
