package com.stc.ruaxe.dtos.transactions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stc.ruaxe.entities.Services;
import com.stc.ruaxe.entities.User;
import com.stc.ruaxe.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String id;

    @DBRef
    private List<Vehicle> vehicle;

    @DBRef
    private List<Services> services;

    @DBRef
    private List<User> users;

    private boolean trangThai = false;

    private String amount;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
    private Date dateTransaction;

}
