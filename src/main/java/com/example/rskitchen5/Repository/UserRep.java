package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRep extends MongoRepository<User, String> {
    Optional<User> findByMail (String mail);
}
