package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRep extends MongoRepository<User, Long> {
    User findByMail(String mail);
}
