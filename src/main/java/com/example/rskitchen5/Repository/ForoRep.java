package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.Foro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForoRep extends MongoRepository<Foro, String> {

}
