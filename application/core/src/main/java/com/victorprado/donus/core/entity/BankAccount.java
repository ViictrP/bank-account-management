package com.victorprado.donus.core.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccount {

    private String id;
    private Customer customer;
    private Double balance;
}
