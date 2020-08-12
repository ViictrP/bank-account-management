package com.victorprado.donus.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Customer {

    private String id;
    private String name;
    private String cpf;

    public Customer() {
        this.id = UUID.randomUUID().toString();
    }

    public Customer(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }
}
