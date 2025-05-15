package com.example.rskitchen5.Repository;
import com.example.rskitchen5.Model.Mesa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MesaRep extends MongoRepository<Mesa, String> {
    Optional<Mesa> findById (String id);
    List<Mesa> findByOcupadoFalse();
}
