package com.stc.ruaxe.repositories;

import com.stc.ruaxe.entities.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VehicleRepository extends MongoRepository<Vehicle,String> {

    
    Page<Vehicle> findByNameContainingAllIgnoreCase(String name, Pageable pageable);

    @Override
    void deleteById(String s);

    Optional<Vehicle> findById(String id);


}
