package com.stc.ruaxe.service.vehicles;

import com.stc.ruaxe.dtos.vehicle.VehicleDto;
import com.stc.ruaxe.entities.Vehicle;
import com.stc.ruaxe.exceptions.InvalidException;
import com.stc.ruaxe.exceptions.NotFoundException;
import com.stc.ruaxe.repositories.VehicleRepository;
import com.stc.ruaxe.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VehicleServiceImpl implements VehicleService {
    public final VehicleRepository dichVuRepository;
    @Override
    public Page<Vehicle> filter(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return dichVuRepository.findByNameContainingAllIgnoreCase(search, pageable);
    }

    @Override
    public void deleteById(String id) {
        dichVuRepository.deleteById(id);
    }

    @Override
    public Vehicle findById(String id) {
        return dichVuRepository.findById(id)
            .orElseThrow(()-> new NotFoundException(String.format("User with id %s does not exist",id)));
    }






    @Override
    public List<Vehicle> findAll() {
       return dichVuRepository.findAll();
    }


    @Override
    public Vehicle create(VehicleDto dto, Principal principal) {
       Vehicle dichVu=new Vehicle();


        if (ObjectUtils.isEmpty(dto.getName())) {
            throw new InvalidException("Tên Dịch Vụ Không được để trống!");
        }



        dichVu.setName(dto.getName());
        dichVuRepository.save(dichVu);

        return dichVu;
    }

    @Override
    public Vehicle update(String id, VehicleDto dto, Principal principal) {
        Optional<Vehicle> vehicle=dichVuRepository.findById(id);
        if(!vehicle.isPresent())
            throw new NotFoundException(String.format("Không tìm thấy dịch vụ có ID %s",id));

        vehicle.get().setName(dto.getName());
        return dichVuRepository.save(vehicle.get());

    }


}
