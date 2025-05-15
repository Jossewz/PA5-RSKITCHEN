package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRep extends MongoRepository<User, String> {
    Optional<User> findByMail (String mail);
    @Query("{ $or: [ {rol: 'MESERO'}, {rol: 'ROLE_MESERO'} ] }")
    List<User> findAllMeseros();
}
