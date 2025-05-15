package com.example.rskitchen5.Service;

import com.example.rskitchen5.Model.User;
import com.example.rskitchen5.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRep userRep;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userRep.findByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con mail: " + mail));

        // Elimina el prefijo ROLE_ ya que Spring lo agregará automáticamente para hasRole()
        // Pero como ahora usamos hasAuthority(), podemos mantener el formato que queramos
        String authority = user.getRol().toUpperCase().startsWith("ROLE_")
                ? user.getRol().toUpperCase()
                : "ROLE_" + user.getRol().toUpperCase();

        return new org.springframework.security.core.userdetails.User(
                user.getMail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(authority))
        );
    }
}
