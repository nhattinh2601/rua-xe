package com.stc.ruaxe.service.transactions;


import com.stc.ruaxe.dtos.services.ServiceDto;

import com.stc.ruaxe.dtos.transactions.TransactionDto;
import com.stc.ruaxe.dtos.user.UserDto;
import com.stc.ruaxe.dtos.vehicle.VehicleDto;
import com.stc.ruaxe.entities.Transactions;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface TransactionService {
    Page<Transactions> filter(String search, int page, int size, String sort, String column);


    public List<Transactions> findAll();

    public TransactionDto create(TransactionDto dto, Principal principal, String vehicleId, String userId, String serviceId) ;

    public Transactions update(String id, TransactionDto dto);
    Transactions findById(String id);

    void deleteById(String id);

    public Transactions changeStatus(String id);
}
