package com.stc.ruaxe.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transaction")
public class Transactions {
    @Id
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
