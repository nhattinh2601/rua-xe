package com.stc.ruaxe.service.services;

import com.stc.ruaxe.dtos.services.ServiceDto;
import com.stc.ruaxe.entities.Services;
import com.stc.ruaxe.exceptions.InvalidException;
import com.stc.ruaxe.exceptions.NotFoundException;
import com.stc.ruaxe.repositories.ServicesRepository;
import com.stc.ruaxe.utils.EnumTrangThaiDatCho;
import com.stc.ruaxe.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServicesServiceImpl implements ServicesService {
    public final ServicesRepository datChoRepository;


    @Override
    public Page<Services> filter(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return datChoRepository.findByIdContainingAllIgnoreCase(search, pageable);
    }




   
    @Override
    public Services findById(String id){
        return datChoRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("User with id %s does not exist",id)));                
    }

    @Override
    public void deleteById(String id) {
        datChoRepository.deleteById(id);
    }


    @Override
    public List<Services> findAll() {
        return datChoRepository.findAll();
    }



    @Override
    public Services create(ServiceDto dto) {

        Services datCho=new Services();

        if (ObjectUtils.isEmpty(dto.getName())) {
            throw new InvalidException("Ten Không được để trống");
        }

        if (ObjectUtils.isEmpty(dto.getDescription())) {
            throw new InvalidException("Mieu ta không được để trống!");
        }

        if (ObjectUtils.isEmpty(dto.getPrice())) {
            throw new InvalidException("Gia không được để trống!");
        }
        datCho.setName(dto.getName());
        datCho.setDescription(dto.getDescription());
        datCho.setPrice(dto.getPrice());


        datChoRepository.save(datCho);

        return datCho;
    }

    @Override
    public Services update(String id, ServiceDto dto) {

        Optional<Services> datCho=datChoRepository.findById(id);
        if(!datCho.isPresent())
            throw new NotFoundException(String.format("Không tìm thấy DatCho có ID %s",id));

        datCho.get().setName(dto.getName());
        datCho.get().setDescription(dto.getDescription());
        datCho.get().setPrice(dto.getPrice());
        return datChoRepository.save(datCho.get());
    }



}
