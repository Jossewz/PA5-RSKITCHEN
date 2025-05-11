package com.example.rskitchen5.Repository;

import com.example.rskitchen5.Model.Mesa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRep extends MongoRepository<Mesa, String> {

    Optional<Mesa> findByNum(int num);

    List<Mesa> findByOcupado(boolean ocupado);

    List<Mesa> findByMeseroId(String meseroId);


    @Query("{ 'pedidosId': { $in: [?0] } }")

    List<Mesa> findMesasByPedidoId(String pedidoId);
    boolean existsByNum(int num);
}