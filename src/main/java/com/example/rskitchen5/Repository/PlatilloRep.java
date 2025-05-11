package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.Platillo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatilloRep extends MongoRepository<Platillo, Long> {
}
