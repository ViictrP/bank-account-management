package com.victorprado.donus.core.usecase.createcustomer;

public class CustomerAlreadyExist extends RuntimeException {

    public CustomerAlreadyExist() {
        super("Customer already exist.");
    }
}
