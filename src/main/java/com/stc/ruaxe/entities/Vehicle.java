package com.stc.ruaxe.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vehicle")
public class Vehicle {
    @Id
    private String id;


    private String name;

    public Vehicle(String name) {
        this.name = name;
    }

}
