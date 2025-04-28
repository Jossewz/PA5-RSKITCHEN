package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRep extends MongoRepository<Producto, String> {
}
