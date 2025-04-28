package com.example.rskitchen5.Controller;

import com.example.rskitchen5.Model.User;
import com.example.rskitchen5.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserRep userRep;

    @GetMapping
    public List<User> getAllUsers() {
        return userRep.findAll();
    }

    @PostMapping
    public User crearUser(@RequestBody User user) {
        return userRep.save(user);
    }


    @PutMapping("/{id}")
    public User updateUsuario(@PathVariable String id, @RequestBody User user) {
        User existingUser = userRep.findById(id).orElseThrow();
        existingUser.setName(user.getName());
        existingUser.setMail(user.getMail());
        existingUser.setRol(user.getRol());
        existingUser.setPassword(user.getPassword());
        return userRep.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userRep.deleteById(id);
    }
}