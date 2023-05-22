package com.stc.ruaxe.service.vehicles;

import com.stc.ruaxe.dtos.vehicle.VehicleDto;
import com.stc.ruaxe.entities.Vehicle;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface VehicleService {
    public Page<Vehicle> filter(String search, int page, int size, String sort, String column);


    List<Vehicle> findAll();

    Vehicle create(VehicleDto dto, Principal principal);

    Vehicle update(String id, VehicleDto dto, Principal principal);

    public Vehicle findById(String id);

    public void deleteById(String id);

}
